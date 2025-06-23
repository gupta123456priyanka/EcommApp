package com.self.ecom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.self.ecom.component.TextComponent
import com.self.ecom.ui.theme.White

@Composable
fun ProductDetailPage(modifier: Modifier = Modifier,  categoryId: String?) {
    Box(Modifier.fillMaxSize().background(White)){
        TextComponent(textVal = "ProductDetailPage $categoryId")
    }
}