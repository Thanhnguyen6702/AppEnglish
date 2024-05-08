package com.example.english4d.ui.customdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Composable
fun DialogRevise(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
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
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_hight)
                    ), text = "Bạn muốn dừng học?", style = TypeText.h4.copy(
                        fontWeight = FontWeight.Bold, color = colorResource(
                            id = R.color.black_50
                        )
                    ), textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_hight),
                        vertical = dimensionResource(
                            id = R.dimen.padding_medium
                        )
                    ),
                    text = "Đừng lo, kết quả của các từ mà bạn đã học vẫn sẽ được lưu lại!",
                    style = TypeText.h8,
                    color = colorResource(
                        id = R.color.black_50
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_hight)),
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_100))
                ) {
                    Text(
                        text = "Tiếp tục ôn tập",
                        style = TypeText.h6.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(
                            id = R.color.white
                        )
                    )
                }
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_hight)),
                    onClick = onConfirmRequest) {
                    Text(
                        text = "Tạm dừng",
                        style = TypeText.h6.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(
                            id = R.color.orange_red
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewA() {
    DialogRevise(onDismissRequest = { /*TODO*/ }) {

    }
}