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
fun SignupScreen(
    modifier: Modifier = Modifier,
    onClickSignupBtn: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG_COLOR)
            .padding(PADDING_OUTERMOST_EACH_SCREEN),
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var name: String by remember { mutableStateOf("") }
        var isLoading: Boolean by remember { mutableStateOf(false) }

        TextComponentH1Black(textVal = stringResource(R.string.hello_there))
        SpacerComponent(heightVal = 10.dp)
        TextComponentH2Black(textVal = stringResource(R.string.create_an_account))
        SpacerComponent(heightVal = 20.dp)
        ImageComponentMiddleScreen(R.drawable.ic_signup)

        OutlinedTextFieldComponent(
            value = email,
            labelVal = stringResource(R.string.email_address),
            onValueChange = { email = it })
        SpacerComponent(heightVal = 20.dp)
        OutlinedTextFieldComponent(
            value = name,
            labelVal = stringResource(R.string.full_name),
            onValueChange = { name = it })
        SpacerComponent(heightVal = 20.dp)
        OutlinedTextFieldComponent(
            value = password,
            labelVal = stringResource(R.string.password),
            onValueChange = { password = it })
        SpacerComponent(heightVal = 20.dp)

        ButtonComponent(textVal = "Signup", isFilled = true, onClick = {
            isLoading = true

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signup(
                    context = context,
                    email = email,
                    password = password,
                    name = name,
                    onResult = { successOrFailureBoolean, errorMessage ->
                        isLoading = false
                        if (successOrFailureBoolean) {
                            AppUtil.showToast(context, msg = "Success" ?: "Signup success")
                            // go to console>authentication and check if user exits
                            // also go to firestore database to check if data is in database
                            // account is created
                            onClickSignupBtn()

                        } else {
                            // account is not created
                            AppUtil.showToast(
                                context,
                                msg = email + " " + errorMessage ?: "Signup failed"
                            )
                        }
                    })
            } else {
                isLoading = false
                AppUtil.showToast(context = context, msg = "email pwd null" ?: "Signup failed")
            }

        }, isEnabled = !isLoading)
//        if (isLoading) {
//            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .width(40.dp)
//                        .height(40.dp)
//                )
//            }
//        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignupScreenPrev() {
    SignupScreen(
        onClickSignupBtn = {},
        modifier = TODO(),
        authViewModel = TODO()
    )
}