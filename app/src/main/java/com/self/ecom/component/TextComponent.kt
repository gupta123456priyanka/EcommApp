package com.self.ecom.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.self.ecom.ui.theme.Black
import com.self.ecom.ui.theme.White

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    textVal: String, fontSizeVal: TextUnit = 16.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.Black
) {
    Text(
        text = textVal, fontSize = fontSizeVal,
        style = TextStyle(
            fontSize = fontSizeVal,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            textAlign = textAlign
        ),
        color = color
    )
}

@Composable
fun TextComponentMoreParams(
    modifier: Modifier = Modifier,
    textVal: String, fontSizeVal: TextUnit = 16.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.Black,

    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    style: TextStyle = TextStyle(
        fontSize = fontSizeVal,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
) {
    Text(
        modifier = modifier,
        text = textVal, fontSize = fontSizeVal,
        style = style,
        color = color,
        overflow = overflow,
        maxLines = maxLines,

        )
}

@Composable
fun TextComponentH1Black(textVal: String, textColor: Color = Black) {
    TextComponent(
        textVal = textVal,
        fontWeight = FontWeight.Bold, fontSizeVal = 30.sp, color = textColor
    )
}

@Composable
fun TextComponentH2Black(textVal: String, textColor: Color = Black) {
    TextComponent(
        textVal = textVal,
        fontSizeVal = 22.sp,
        color = textColor
    )
}

@Composable
fun TextComponentH3Black(textVal: String, textColor: Color = Black, modifier: Modifier = Modifier) {
    TextComponent(
        textVal = textVal,
        fontSizeVal = 18.sp,
        color = textColor,
        modifier = modifier
    )
}

@Composable
fun TextComponentH3White(textVal: String) {
    TextComponent(
        textVal = textVal,
        fontSizeVal = 10.sp,
        color = White
    )
}

@Composable
fun TextComponentH4White(textVal: String) {
    TextComponent(
        textVal = textVal,
        fontSizeVal = 7.sp,
        color = White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextComponentH2White(textVal: String) {
    TextComponent(
        textVal = textVal,
        fontSizeVal = 22.sp,
        color = White
    )
}
