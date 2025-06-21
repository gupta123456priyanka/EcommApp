package com.self.ecom.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.self.ecom.R

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

@Composable
fun ImageComponentMiddleScreen(icSignup: Int) {
    ImageComponent(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp), R.drawable.ic_signup
    )
}