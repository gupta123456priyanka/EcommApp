package com.self.ecom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.self.ecom.component.TextComponentH2Black
import com.self.ecom.ui.theme.White

@Composable
fun CategoryProductsPage(modifier: Modifier = Modifier, categoryId : String ="") {
    Box (Modifier.fillMaxSize().background(White)){

        TextComponentH2Black(textVal = "CategoryProductsPage, id = $categoryId")
    }

}