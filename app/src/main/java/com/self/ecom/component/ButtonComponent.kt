package com.self.ecom.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.self.ecom.ui.theme.Blue30
import com.self.ecom.ui.theme.White

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(4.dp), textVal: String,
    isFilled: Boolean = false,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    val textColor = if (isFilled) White else Blue30
    val bgColor = ButtonDefaults.buttonColors(
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        containerColor = if (isFilled) Blue30 else White
    )

    if (isFilled) {
        Button(onClick = onClick, modifier = modifier, colors = bgColor, enabled = isEnabled) {
            ButtonInsideComposable(textVal = textVal, textColor = textColor)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            colors = bgColor,
            enabled = isEnabled
        ) {
            ButtonInsideComposable(textVal = textVal, textColor = textColor)
        }
    }
}

@Composable
fun ButtonInsideComposable(
    modifier: Modifier = Modifier,
    textVal: String,
    textColor: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textVal,
            fontSize = 22.sp,
            modifier = Modifier.padding(4.dp),
            color = textColor
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OutlinedButtonCompPrev() {
    ButtonComponent(textVal = "Login", isFilled = true, onClick = {}, isEnabled = true)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ButtonComponentPrev() {
    ButtonComponent(textVal = "Signup", isFilled = false, onClick = {}, isEnabled = true)
}