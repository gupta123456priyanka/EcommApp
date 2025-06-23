package com.self.ecom.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButtonComposable(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = "")
    }
}