package com.example.medicapp.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.AlertDialog
import com.example.medicapp.ui.theme.ButtonEnabledColor

@Composable
fun MainAlertDialog(
    openDialog: MutableState<Boolean>,
    responseCode: MutableState<Int>,
    time: MutableState<Int> = mutableStateOf(0),
    titleTextIf: String,
    titleTextElse: String,
    contentTextIf: String,
    contentTextElse: String,
) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
            responseCode.value = 200
            time.value = 30
        },

        title = { Text(text = if (responseCode.value == 422) titleTextIf else titleTextElse) },
        text = { Text(text = if (responseCode.value == 422) contentTextIf else contentTextElse) },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        openDialog.value = false
                        responseCode.value = 200
                        time.value = 30
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonEnabledColor)
                ) {
                    Text(text = "Ok", color = Color.White)
                }
            }
        }
    )
}