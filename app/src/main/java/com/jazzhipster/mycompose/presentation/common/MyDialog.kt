package com.jazzhipster.mycompose.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    dismissDialog: () -> Unit
) {

    Dialog(
        onDismissRequest = { dismissDialog() },
        properties = DialogProperties(false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(16.dp)

            ) {
                CircularProgressIndicator()
            }
        }
    }

}

@Composable
fun ShowMessageDialog(
    title: String,
    message: String,
    dismissDialog: () -> Unit,
    buttonColor: Color = Color.Blue,
    positiveText: String = "確認",
    negativeText: String = "取消",
    negativeListener: (() -> Unit)? = null,
    positiveListener: () -> Unit
) {

    Dialog(
        onDismissRequest = { dismissDialog() },
        properties = DialogProperties(false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(text = message, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = { negativeListener?.invoke() },
                            modifier = Modifier.weight(1f),
                            enabled = negativeListener != null
                        ) {
                            if (negativeListener != null)
                                Text(text = negativeText, fontSize = 14.sp, color = buttonColor)
                        }
                        TextButton(
                            onClick = { positiveListener() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = positiveText, fontSize = 14.sp, color = buttonColor)
                        }
                    }
                }
            }
        }
    }

}