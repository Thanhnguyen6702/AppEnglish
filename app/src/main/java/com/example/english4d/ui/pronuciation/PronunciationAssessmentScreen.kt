package com.example.english4d.ui.pronuciation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.animation.CustomCircularProgressIndicator
import com.example.english4d.ui.animation.OscillatingBars
import com.example.english4d.ui.theme.TypeText


@Composable
fun PronunciationAssessmentScreen(
    navController: NavController,
    viewModel: PronunciationAssessmentViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiStateAssessment.collectAsState()
    val context = LocalContext.current

    // Biến để theo dõi trạng thái quyền
    var hasPermissions by remember {
        mutableStateOf(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    // Launcher để yêu cầu quyền
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermissions = permissions.all { it.value }
    }
    Scaffold {
        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_hight),
                        top = dimensionResource(id = R.dimen.padding_hight),
                        bottom = dimensionResource(id = R.dimen.padding_hight)
                    )
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boy),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.padding_hight))
                        .size(48.dp)
                )
                when (uiState.isRecording) {
                    RecordingState.RECORDING -> Text(
                        text = "Mình đang nghe ...", style = TypeText.h7.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )

                    RecordingState.NOTRECORDING -> when (uiState.score) {
                        -1 -> Text(
                            text = "Vui lòng phát âm lại", style = TypeText.h7.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )

                        0 -> Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Nhấn vào biểu tượng ", style = TypeText.h7.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = null,
                                tint = colorResource(
                                    id = R.color.green_100
                                )
                            )
                            Text(
                                text = " để bắt đầu", style = TypeText.h7.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        else -> Text(
                            text = "Điểm của bạn là ${uiState.score}", style = TypeText.h7.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    RecordingState.PROCESSING -> Text(
                        text = "Đang tính điểm", style = TypeText.h7.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }


            }
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_hight)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = uiState.vocabulary.english,
                        style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_hight)),
                    )
                    Text(
                        text = uiState.vocabulary.pronunciation,
                        style = TypeText.h5,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_hight)),
                    )
                    Text(
                        text = uiState.vocabulary.vietnamese,
                        style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_hight)),
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.score > 0) {
                        CustomCircularProgressIndicator(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = colorResource(id = R.color.white)),
                            initialValue = uiState.score,
                            primaryColor = when (uiState.score) {
                                in 1..49 -> colorResource(id = R.color.red)
                                in 50..74 -> colorResource(id = R.color.orange_100)
                                else -> colorResource(id = R.color.green_100)
                            },
                            secondaryColor = when (uiState.score) {
                                in 0..49 -> colorResource(id = R.color.red_20)
                                in 50..74 -> colorResource(id = R.color.orange_20)
                                else -> colorResource(id = R.color.green_20)
                            },
                            circleRadius = 100f
                        ) {

                        }
                    }
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = colorResource(id = R.color.green_10),
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    IconButton(
                        onClick = { viewModel.textToSpeech(uiState.vocabulary.english) }, enabled = !uiState.isSpeak
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = null
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = if (uiState.isRecording == RecordingState.PROCESSING) {
                                colorResource(id = R.color.gray_20)
                            } else {
                                colorResource(id = R.color.green_100)
                            },
                            shape = MaterialTheme.shapes.large
                        )
                        .clickable {
                            if (hasPermissions) {
                                viewModel.startPronun()
                            } else {
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.RECORD_AUDIO
                                    )
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isRecording == RecordingState.NOTRECORDING) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.white
                            )
                        )
                    } else if (uiState.isRecording == RecordingState.RECORDING) {
                        OscillatingBars()
                    } else {
                        Icon(imageVector = Icons.Filled.Mic, contentDescription = null)
                    }
                }
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = colorResource(id = R.color.green_10),
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    IconButton(
                        onClick = { viewModel.listeningAgain() },
                        enabled = uiState.isListen
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Headphones,
                            contentDescription = null,
                            tint = if (uiState.isListen) {
                                colorResource(id = R.color.green_100)
                            } else {
                                colorResource(id = R.color.gray_20)
                            }
                        )
                    }
                }
            }
            ElevatedButton(
                onClick = {
                    viewModel.nextPronunciation()
                },
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.padding_hight))
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green_10)
                )
            ) {
                Text(
                    text = "Từ tiếp theo", style = TypeText.h6.copy(
                        fontWeight = FontWeight.Bold, color = colorResource(
                            id = R.color.green_100
                        )
                    )
                )
            }
        }
    }
}
