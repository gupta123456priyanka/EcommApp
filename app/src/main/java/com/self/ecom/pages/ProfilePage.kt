package com.self.ecom.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.self.ecom.AppUtil
import com.self.ecom.R
import com.self.ecom.component.ButtonComponent
import com.self.ecom.component.ImageComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponentH3Black
import com.self.ecom.component.TextComponentH4Black
import com.self.ecom.model.UserModel

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    onClickMyOrders: () -> Unit,
    onClickSignout: () -> Unit
) {
    val context = LocalContext.current

    var userModel by remember { mutableStateOf(UserModel()) }
    var addressInput by remember { mutableStateOf(userModel.address) }
    LaunchedEffect(Unit) {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val model = it.result.toObject(UserModel::class.java)
                if (model != null) {
                    userModel = model
                    addressInput = userModel.address
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
//                , bottom = 150.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp) // reserve space for the button
        ) {
            TextComponentH4Black(textVal = "Your profile")

//            TextComponentH2Black(textVal = "${userModel.toString()}")
            ImageComponent(resourceValue = R.drawable.profile)

            TextComponentH3Black(textVal = userModel.name)
            SpacerComponent(heightVal = 10.dp)

            TextComponentH3Black(textVal = "Address : ")
            TextField(
                onValueChange = { addressInput = it }, value = addressInput,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done), // adding done btn in keyboard
                keyboardActions = KeyboardActions(onDone = {
                    // update to firestore
                    if (addressInput.isNotEmpty()) {
                        Firebase.firestore.collection("users")
                            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .update("address", addressInput).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    AppUtil.showToast(
                                        context = context,
                                        msg = "address edited successfully"
                                    )
                                }

                            }
                    } else {
                        AppUtil.showToast(context = context, msg = "address editing Failed")

                    }

                })
            )
            SpacerComponent(heightVal = 10.dp)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextComponentH3Black(textVal = "Email : ")
                TextComponentH4Black(textVal = userModel.email)
            }
            SpacerComponent(heightVal = 10.dp)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextComponentH3Black(textVal = "Items in cart:")
                TextComponentH4Black(textVal = userModel.cartItems.values.sum().toString())
            }
            ButtonComponent(
                textVal = "My Orders",
                isFilled = true,
                onClick = onClickMyOrders,
                isEnabled = true
            )


            ButtonComponent(
                textVal = "Sign Out",
                isFilled = true,
                onClick = onClickSignout,
                isEnabled = true
            )

        }
    }
}