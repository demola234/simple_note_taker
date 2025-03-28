package com.example.notetaker.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun NoteInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit,
    imeAction: androidx.compose.ui.text.input.ImeAction = androidx.compose.ui.text.input.ImeAction.Default,
    label: String,
    color: Color,
    maxLines: Int = 1,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.colors(
            cursorColor = color,
            disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        modifier = modifier,
        label = { androidx.compose.material3.Text(text = label) },
        maxLines = maxLines,
        singleLine = maxLines == 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
                keyboardController?.hide()
            }
        )
    )
}