package com.self.ecom.component

import android.R.attr.height
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    resourceValue: Int? = null
) {

    Box() {
        resourceValue?.also {
            Image(
                modifier = modifier ,
                painter = painterResource(resourceValue),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
    }
}