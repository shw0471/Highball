package com.semicolon.highball.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBar(
    text: String,
    isTop: Boolean = true
) {
    if (isTop) {
        Text(
            text = text,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 30.dp,
                    bottom = 10.dp
                ),
            maxLines = 1
        )
    } else {
        Column(
            modifier = Modifier
                .background(Color(0x11000000))
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(),
                style = TextStyle(textAlign = TextAlign.Center)
            )

            Divider(color = Color(0x33000000))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview1() {
    AppBar("Test", true)
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview2() {
    AppBar("Test", false)
}