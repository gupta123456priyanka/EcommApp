package com.self.ecom.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.self.ecom.AppUtil
import com.self.ecom.Constants.BG_COLOR
import com.self.ecom.Constants.PADDING_OUTERMOST_EACH_SCREEN
import com.self.ecom.R
import com.self.ecom.component.ButtonComponent
import com.self.ecom.component.ImageComponentMiddleScreen
import com.self.ecom.component.OutlinedTextFieldComponent
import com.self.ecom.component.SpacerComponent
import com.self.ecom.component.TextComponentH1Black
import com.self.ecom.component.TextComponentH2Black
import com.self.ecom.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onClickLoginBtn: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading: Boolean by remember { mutableStateOf(false) }
    val context = LocalContext.current

    email = "ecom1@gmail.com"
    password = "123456"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG_COLOR)
            .padding(PADDING_OUTERMOST_EACH_SCREEN),
        verticalArrangement = Arrangement.Center
    ) {
        TextComponentH1Black(textVal = stringResource(R.string.welcome_back))
        SpacerComponent(heightVal = 10.dp)
        TextComponentH2Black(textVal = stringResource(R.string.signin_to_your_account))
        SpacerComponent(heightVal = 20.dp)
        ImageComponentMiddleScreen(R.drawable.ic_signup)
        OutlinedTextFieldComponent(
            value = email,
            labelVal = stringResource(R.string.email_address),
            onValueChange = { email = it })
        SpacerComponent(heightVal = 20.dp)
        OutlinedTextFieldComponent(
            value = password,
            labelVal = stringResource(R.string.password),
            onValueChange = { password = it })
        SpacerComponent(heightVal = 20.dp)

        ButtonComponent(
            textVal = "Login",
            isFilled = true,
            onClick = {
                isLoading = true

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.login(
                        email = email, password = password, context = context,
                        onResult = { successOrFailureBoolean, errorMessage ->
                            isLoading = false
                            if (successOrFailureBoolean) {
                                AppUtil.showToast(context = context, msg = "Success")
                                onClickLoginBtn()
                            } else {
                                AppUtil.showToast(context = context, msg = errorMessage ?: "Error")
                            }
                        })
                } else {
                    AppUtil.showToast(context = context, msg = "email pwd null" ?: "Login failed")
                    isLoading = false
                }

            },
            isEnabled = !isLoading
        )
    }
}

@Preview
@Composable
private fun LoginScreenPrev() {
    LoginScreen(onClickLoginBtn = {})
}