package com.self.ecom.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldComponent(modifier: Modifier = Modifier.fillMaxWidth(), initVal: String, labelVal: String) {
    var email by remember {
        mutableStateOf(initVal)
    }
    OutlinedTextField(modifier = modifier,value = email, onValueChange = {
        email = it
    }, label = { Text(text = labelVal) })
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun TextFieldComponentPrev() {

    TextFieldComponent(initVal = "", labelVal = "Email Address")
}