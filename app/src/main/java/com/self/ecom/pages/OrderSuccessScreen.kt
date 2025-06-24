package com.self.ecom.pages

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.self.ecom.AppUtil
import com.self.ecom.component.TextComponent
import com.self.ecom.ui.theme.White

@Composable
fun OrderSuccessScreen(modifier: Modifier = Modifier, onClick: () -> Unit, context: Context) {
    AppUtil.clearCartAndAddToOrders()
    // check in firestore that order is created and cartitem is removed

//        AppUtil.showToast(msg = "Payment Success, $p0", context =this)

    val builder = AlertDialog.Builder(context)
    builder.setTitle("Payment successful")
    builder.setMessage("Thank you, Your payment was completed successfully and your order has been placed")
    builder.setPositiveButton("OK") { _, _ ->
        onClick()
    }.setCancelable(false).show()

    Box(
        Modifier
            .fillMaxSize()
            .background(White)
    ) {
        TextComponent(textVal = "OrderSuccessScreen", fontSizeVal = 40.sp)
    }
}