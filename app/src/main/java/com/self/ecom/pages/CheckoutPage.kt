package com.self.ecom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.self.ecom.AppUtil
import com.self.ecom.R
import com.self.ecom.component.ButtonComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponent
import com.self.ecom.component.TextComponentMoreParams
import com.self.ecom.model.ProductModel
import com.self.ecom.model.UserModel
import com.self.ecom.ui.theme.White

@Composable
fun CheckoutPage(
    modifier: Modifier = Modifier,
    onClickPay: (Float) -> Unit,
) {
    var userModel by remember { mutableStateOf(UserModel()) }
    val productList = remember { mutableStateListOf<ProductModel>(ProductModel()) }
    var totalPrice by remember { mutableStateOf(0.0f) }
    var discount by remember { mutableStateOf(0.0f) }
    var tax by remember { mutableStateOf(0.0f) }
    var total by remember { mutableStateOf(0.0f) }

    fun calculateAndAssign() {
        productList.forEach { product: ProductModel ->
            if (product.actualPrice.isNotEmpty()) {
                val qty = userModel.cartItems.get(product.id) ?: 0
                totalPrice += product.actualPrice.toFloat() * qty
            }
            discount = totalPrice * (AppUtil.getDiscountPercentage() / 100)
            totalPrice = totalPrice - discount
            tax = totalPrice * (AppUtil.getTaxPercentage() / 100)
            totalPrice = totalPrice - tax


        }
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel = result
                    }

                    Firebase.firestore.collection("data").document("stock").collection("products")
                        .whereIn("id", userModel.cartItems.keys.toList())

                        .get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // objects, not object, bcoz we have list
                                val resultProducts = task.result.toObjects(ProductModel::class.java)
                                productList.addAll(resultProducts)
                                calculateAndAssign()
                            } else {

                            }
                        }
                } else {

                }
            }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 40.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TextComponent(
                textVal = "Checkout ",
                fontSizeVal = 30.sp, fontWeight = FontWeight.SemiBold
            )
            SpacerComponent(heightVal = 10.dp)
            HorizontalDivider()


            Column(Modifier.padding(8.dp)) {
                SpacerComponent(heightVal = 10.dp)

                TextComponent(
                    Modifier.padding(8.dp),
                    textVal = "Deliver to :",
                    fontWeight = FontWeight.SemiBold,
                    fontSizeVal = 20.sp
                )

                SpacerComponent(heightVal = 10.dp)
                TextComponent(
                    Modifier.padding(8.dp),
                    textVal = userModel.address,
                    fontSizeVal = 18.sp,
                    textAlign = TextAlign.Start
                )
            }
            SpacerComponent(heightVal = 10.dp)
            HorizontalDivider()
            RowCheckoutItems(label = "Discount", value = discount.toString())
            RowCheckoutItems(label = "Tax", value = tax.toString())
            RowCheckoutItems(label = "Total Price", value = totalPrice.toString())

            HorizontalDivider()
            SpacerComponent(heightVal = 10.dp)
            TextComponentMoreParams(
                modifier = Modifier.fillMaxWidth(),
                textVal = stringResource(R.string.to_pay),
                fontSizeVal = 16.sp, textAlign = TextAlign.Center
            )

            SpacerComponent(heightVal = 10.dp)
            total = "%.2f".format(totalPrice).toFloat() //  show decimal upto 2
            TextComponentMoreParams(
                modifier = Modifier.fillMaxWidth(),
                textVal = "$" + total,
                fontSizeVal = 30.sp, textAlign = TextAlign.Center
            )

            SpacerComponent(heightVal = 10.dp)

            ButtonComponent(
                textVal = "Pay Now",
                isFilled = true,
                onClick = {onClickPay(total)},
                isEnabled = true
            )
            //paypal, stripe, razorpay


        }


    }
}

@Composable
fun RowCheckoutItems(modifier: Modifier = Modifier, label: String, value: String) {

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextComponent(textVal = label, fontWeight = FontWeight.SemiBold, fontSizeVal = 20.sp)
            TextComponent(textVal = value, fontSizeVal = 18.sp)
        }
    }
}