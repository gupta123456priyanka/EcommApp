package com.self.ecom.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.self.ecom.component.IconButtonComposable
import com.self.ecom.component.ImageComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponentH2Black
import com.self.ecom.component.TextComponentMoreParams
import com.self.ecom.model.ProductModel
import com.self.ecom.model.UserModel
import com.self.ecom.ui.theme.Black
import com.self.ecom.ui.theme.White

@Composable
fun CartPage(
    modifier: Modifier = Modifier,
    onClickAddToCart: (String) -> Unit,
    onClickRemoveFromCart: (String) -> Unit,
    onClickRemoveAllCart: (String) -> Unit
) {
    var usermodel by remember { mutableStateOf(UserModel()) }

    DisposableEffect(key1 = Unit) {
        var listener = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener { it, _ ->
                if (it != null) {
                    val result = it.toObject(UserModel::class.java)
                    if (result != null) {
                        usermodel = result
                    }
                }
            }
        onDispose { listener.remove() }

        // here we are initialising the listener , not removing it, so it can be triggered multiple times,
        // to avoid this use DisposableEffect instead of LaunchedEffect
        /*   .get().addOnCompleteListener { // use snapshot listener for updating quantity
               if (it.isSuccessful) {
                   val result = it.result.toObject(UserModel::class.java)
                   if (result != null) {
                       usermodel = result
                   }
               }
           }*/
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        TextComponentH2Black(textVal = "Your Cart  ")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // it.first means first param i.e. productId
            items(usermodel.cartItems.toList(), key = {it.first}) { (productId, qty) ->
                CartItemView(
                    modifier = Modifier, productId = productId,
                    qty = qty, onClickAddToCart = onClickAddToCart,
                    onClickCategory = {},
                    onClickRemoveFromCart = onClickRemoveFromCart,
                    onClickRemoveAllCart = onClickRemoveAllCart
                )
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun CartItemView(
    productId: String, modifier: Modifier = Modifier, qty: Long,
    onClickCategory: (String) -> Unit,
    onClickAddToCart: (String) -> Unit,
    onClickRemoveFromCart: (String) -> Unit,
    onClickRemoveAllCart: (String) -> Unit
) {
    var productModel by remember {
        mutableStateOf(ProductModel())
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stock").collection("products")
            .document(productId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result: ProductModel? = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        productModel = result
                    }
                }
            }
    }
    SpacerComponent(heightVal = 10.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .height(120.dp)
            .clickable {},
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(7.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {


            ImageComponent(
                url = productModel.images.firstOrNull(),
                modifier = Modifier
                    .height(100.dp)
                    .width(80.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                TextComponentMoreParams(
                    textVal = productModel.title,
                    fontSizeVal = 18.sp,
                    color = Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                TextComponentMoreParams(
                    textVal = "$" + productModel.actualPrice,
                    fontSizeVal = 14.sp,
                    color = Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onClickRemoveFromCart(productId) }) {
                        TextComponentMoreParams(
                            textVal = "-",
                            fontSizeVal = 14.sp,
                            color = Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    TextComponentMoreParams(
                        textVal = qty.toString(),
                        fontSizeVal = 14.sp,
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.padding(8.dp)
                    )
                    IconButton(onClick = { onClickAddToCart(productId) }) {
                        TextComponentMoreParams(
                            textVal = "+",
                            fontSizeVal = 14.sp,
                            color = Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            IconButtonComposable(
                onClick = { onClickRemoveAllCart(productId) },
                imageVector = Icons.Filled.Delete
            )
        }
    }
}

