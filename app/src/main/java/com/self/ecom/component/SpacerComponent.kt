package com.self.ecom.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerComponent(modifier: Modifier = Modifier,
                    heightVal: Dp ) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(heightVal)
    )
}