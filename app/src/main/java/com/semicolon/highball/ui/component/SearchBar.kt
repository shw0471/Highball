package com.semicolon.highball.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun SearchBar(
    onSearchClick: (keyword: String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var buttonSize by remember { mutableStateOf(if (isFocused) 80.dp else 0.dp) }

    if (true) {
        ConstraintLayout(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .height(40.dp)
                .fillMaxSize()
                .padding(0.dp)
        ) {
            val (textField, cancelButton) = createRefs()

            SearchTextField(
                modifier = Modifier
                    .constrainAs(textField) {
                        start.linkTo(parent.absoluteLeft)
                        end.linkTo(cancelButton.absoluteLeft)
                        width = Dimension.fillToConstraints
                    },
                onSearchClick = {
                    onSearchClick(it)
                    isFocused = false
                    buttonSize = 0.dp
                },
                onFocused = {
                    buttonSize = 80.dp
                }
            )

            CancelSearchButton(
                modifier = Modifier
                    .requiredWidth(buttonSize)
                    .constrainAs(cancelButton) {
                        end.linkTo(parent.absoluteRight)
                    },
                onClick = {
                    isFocused = false
                    buttonSize = 0.dp
                }
            )
        }
    } else {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .height(40.dp)
                .fillMaxSize()
                .padding(0.dp)
        ) {
            SearchTextField(
                onSearchClick = { onSearchClick(it) },
                onFocused = { isFocused = true }
            )
        }
    }
}

@Composable
fun SearchTextField(
    onSearchClick: (keyword: String) -> Unit,
    onFocused: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 0.dp)
            .height(40.dp)
            .background(
                Color(0xFFCCCCCC),
                RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search",
            modifier = Modifier
                .height(40.dp)
                .width(24.dp)
        )

        BasicTextField(
            value = text,
            onValueChange = { text = it },
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
                if (text.isEmpty()) return@KeyboardActions
                onSearchClick(text)
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(start = 0.dp)
                .padding(start = 10.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) onFocused()
                }
        )
    }
}

@Composable
fun CancelSearchButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextButton(
        onClick = {
            focusManager.clearFocus()
            onClick()
        },
        content = {
            Text(
                text = "Cancel",
                fontSize = 16.sp,
                color = Color(0xFF4787F7),
                modifier = Modifier
                    .wrapContentSize()
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        onSearchClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        modifier = Modifier,
        onSearchClick = {},
        onFocused = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CancelSearchButtonPreview() {
    CancelSearchButton {

    }
}
