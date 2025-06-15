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
import com.self.ecom.component.TextFieldComponent

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onClickLoginBtn: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG_COLOR)
            .padding(PADDING_OUTERMOST_EACH_SCREEN),
        verticalArrangement = Arrangement.Center
    ) {
        TextComponent(
            textVal = stringResource(R.string.welcome_back),
            fontWeight = FontWeight.Bold, fontSizeVal = 30.sp
        )
        SpacerComponent(heightVal = 10.dp)
        TextComponent(
            textVal = stringResource(R.string.signin_to_your_account),
            fontSizeVal = 22.sp
        )
        SpacerComponent(heightVal = 20.dp)
        ImageComponent(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(200.dp), R.drawable.ic_signup
        )

        TextFieldComponent(initVal = "", labelVal = stringResource(R.string.email_address))
        SpacerComponent(heightVal = 20.dp)
        TextFieldComponent(initVal = "", labelVal = stringResource(R.string.password))
        SpacerComponent(heightVal = 20.dp)
        ButtonComponent(textVal = "Login", isFilled = true, onClick = onClickLoginBtn)

    }
}

@Preview
@Composable
private fun LoginScreenPrev() {
    LoginScreen(onClickLoginBtn = {})
}