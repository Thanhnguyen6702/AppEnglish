package com.example.english4d.ui.customdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Composable
fun DialogAddWord(
    onDismiss: () -> Unit,
    addWord: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_hight
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Nhập từ muốn thêm",
                    style = TypeText.h5.copy(fontWeight = FontWeight.Medium)
                )
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    isError = isError,
                    label = { Text(text = "Nhập từ") }
                )
                if (isError) {
                    Text(
                        text = errorMessage,
                        color = colorResource(id = R.color.red)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.width(120.dp),
                        onClick = {
                            if (text.isBlank()) {
                                isError = true
                                errorMessage = "Không được để trống"
                            } else {
                                isError = false
                                addWord(text)
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green_100)
                        )
                    ) {
                        Text(text = "Thêm từ")
                    }
                    Button(
                        modifier = Modifier.width(120.dp),
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.red)
                        )
                    ) {
                        Text(text = "Hủy")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogAddWordPreview() {
    DialogAddWord(onDismiss = {}, addWord = {})
}