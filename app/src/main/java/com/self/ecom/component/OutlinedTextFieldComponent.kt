package com.self.ecom.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.self.ecom.ui.theme.Black

@Composable
fun OutlinedTextFieldComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: (String) -> Unit,
    labelVal: String,
    value: String
) {

    OutlinedTextField(modifier = modifier, value = value, onValueChange = onValueChange, label = {
        Text(text = labelVal, color = Black)
    }, textStyle = TextStyle.Default.copy(color = Black))

}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun OutlinedTextFieldComponentPrev() {

    OutlinedTextFieldComponent(value = "abc", labelVal = "Email Address", onValueChange = {

    })
}