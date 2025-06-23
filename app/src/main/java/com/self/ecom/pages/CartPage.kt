package com.self.ecom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.self.ecom.component.TextComponentH2Black
import com.self.ecom.component.TextComponentH2White
import com.self.ecom.model.UserModel
import com.self.ecom.ui.theme.White

@Composable
fun CartPage(modifier: Modifier = Modifier) {
    var usermodel by remember { mutableStateOf(UserModel()) }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        usermodel = result
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {


        TextComponentH2Black(textVal = "Your Cart  ")
        LazyColumn {
            items(usermodel.cartItems.toList()) { (productId, quantity) ->
                CartItemView(
                    modifier = Modifier, productId = productId,
                    quantity = quantity
                )
            }

        }
    }

}

@Composable
fun CartItemView(productId: String, modifier: Modifier = Modifier, quantity: Long) {
    Card() {
        Spacer(Modifier
            .height(8.dp)
            .fillMaxWidth()
            .background(White))
        Column(Modifier.padding(16.dp)) {


            TextComponentH2White(textVal = "productId = $productId  ")

            TextComponentH2White(textVal = "quantity = $quantity  ")
        }
    }
}