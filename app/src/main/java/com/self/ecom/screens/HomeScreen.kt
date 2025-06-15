package com.self.ecom.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.self.ecom.Constants.BG_COLOR
import com.self.ecom.component.TextComponent

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column (Modifier.fillMaxSize().background(BG_COLOR)){
        TextComponent(textVal = "HomeScreen")
    }
}