package com.example.sparklington

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class GoatType(val color: Color) {
    BASIC(Color.Gray),
    CULTURE1(Color.Cyan),
    CULTURE2(Color(0xFF90EE90))
}

@Composable
fun GoatTabContent(onDonateClicked: () -> Unit) {
    var feedCount by remember { mutableStateOf(0) }
    var tempFeedCount by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var showBalloon by remember { mutableStateOf(false) }
    var balloonType by remember { mutableStateOf("") }
    var showQuizOptions by remember { mutableStateOf(false) }
    var showAnswerBalloon by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var goatType by remember { mutableStateOf(GoatType.BASIC) }

    val maxFeedCount = 100
    val feedPercentage = (feedCount.toFloat() / maxFeedCount) * 100

    LaunchedEffect(tempFeedCount) {
        if (tempFeedCount > 0) {
            delay(2000)

            if ((0..4).random() == 0) {
                balloonType = if ((0..1).random() == 0) "퀴즈" else "소식"
                showBalloon = true
                if (balloonType == "퀴즈") {
                    showQuizOptions = true
                }
            }

            tempFeedCount = 0
        }
    }

    LaunchedEffect(showBalloon) {
        if (showBalloon) {
            isButtonEnabled = false
            delay(10000)
            showBalloon = false
            showQuizOptions = false
            isButtonEnabled = true
        }
    }

    LaunchedEffect(showAnswerBalloon) {
        if (showAnswerBalloon) {
            isButtonEnabled = false
            delay(3000)
            showAnswerBalloon = false
            isButtonEnabled = true
        }
    }

    LaunchedEffect(Unit) {
        feedCount = 0
        goatType = GoatType.entries.random()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "염소가 성체가 되었습니다")
            },
            text = {
                Text(text = "염소를 기부하겠습니까?")
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDonateClicked()
                }) {
                    Text("기부하기")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("취소")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "염소",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "${feedPercentage.toInt()}%", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat((feedPercentage / 20).toInt()) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Green, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            repeat(5 - (feedPercentage / 20).toInt()) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 염소 이미지 자리
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(goatType.color),
            contentAlignment = Alignment.TopCenter
        ) {
            if (showBalloon) {
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = balloonType,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            if (showAnswerBalloon) {
                Box(
                    modifier = Modifier
                        .background(Color.Yellow, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "정답입니다!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 염소 종류 텍스트 표시
        Text(
            text = when (goatType) {
                GoatType.BASIC -> "기본 염소"
                GoatType.CULTURE1 -> "문화 염소 1"
                GoatType.CULTURE2 -> "문화 염소 2"
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (feedCount < maxFeedCount && isButtonEnabled) {
                    feedCount++
                    tempFeedCount++
                }

                if (feedCount == maxFeedCount) {
                    showDialog = true
                }
            },
            modifier = Modifier.padding(8.dp),
            enabled = isButtonEnabled
        ) {
            Text(text = "먹이주기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "X $tempFeedCount", fontSize = 24.sp)

        if (showQuizOptions) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Button(onClick = {
                    showBalloon = false
                    showQuizOptions = false
                    showAnswerBalloon = true
                }) {
                    Text(text = "O")
                }
                Button(onClick = {
                    showBalloon = false
                    showQuizOptions = false
                    showAnswerBalloon = true
                }) {
                    Text(text = "X")
                }
            }
        }
    }
}





