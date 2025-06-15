package com.self.ecom.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.self.ecom.Constants.BG_COLOR
import com.self.ecom.Constants.PADDING_OUTERMOST_EACH_SCREEN
import com.self.ecom.R
import com.self.ecom.component.ButtonComponent
import com.self.ecom.component.ImageComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponent

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
    onClickSignup: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize().background(BG_COLOR)
            .padding(PADDING_OUTERMOST_EACH_SCREEN), verticalArrangement = Arrangement.Center
    ) {
        ImageComponent(
            Modifier
                .fillMaxWidth()
                .height(300.dp),
            resourceValue = R.drawable.ic_banner
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextComponent(
                textVal = stringResource(R.string.start_desc),
                fontSizeVal = 30.sp, fontWeight = FontWeight.SemiBold
            )
            SpacerComponent(heightVal = 10.dp)
            TextComponent(
                textVal = stringResource(R.string.auth_desc),
                fontSizeVal = 16.sp
            )
            SpacerComponent(heightVal = 20.dp)
            ButtonComponent(textVal = "Login", isFilled = true, onClick = onClickLogin)
            SpacerComponent(heightVal = 10.dp)
            ButtonComponent(textVal = "Signup", isFilled = false, onClick = onClickSignup)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AuthScreenPrev() {
    AuthScreen(onClickLogin = {}, onClickSignup = {})

}