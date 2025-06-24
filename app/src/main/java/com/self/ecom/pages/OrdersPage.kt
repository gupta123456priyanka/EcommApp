package com.self.ecom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.self.ecom.component.TextComponentH2Black
import com.self.ecom.ui.theme.White

@Composable
fun OrdersPage(modifier: Modifier = Modifier) {

    Box(Modifier
        .fillMaxSize()
        .background(White)) {
        Column(verticalArrangement = Arrangement.Center) {

            TextComponentH2Black(textVal = "OrdersPage")
        }
    }
}