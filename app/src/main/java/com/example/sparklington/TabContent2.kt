package com.example.sparklington

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.sparklington.data.Quiz
import com.example.sparklington.data.QuizData

enum class GoatType(val id: Int, val color: Color) {
    BASIC(1, Color.Transparent),
    CULTURE1(2, Color.Transparent),
    CULTURE2(3, Color.Transparent);

    companion object {
        fun fromId(id: Int): GoatType {
            return values().find { it.id == id } ?: BASIC
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun GoatTabContent(onDonateClicked: () -> Unit) {
    var tempFeedCount by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var showQuizOptions by remember { mutableStateOf(false) }
    var showAnswerBox by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var quiz by remember { mutableStateOf<Quiz?>(null) }
    var answerText by remember { mutableStateOf("") }

    val goatType = GoatType.fromId(UserDataHolder.goat_age / 1000)
    val feedCount = UserDataHolder.goat_age % 1000

    val maxFeedCount = 100
    val feedPercentage = (feedCount.toFloat() / maxFeedCount) * 100

    var localHayNum by remember { mutableStateOf(UserDataHolder.hay_num) }

    LaunchedEffect(tempFeedCount) {
        if (tempFeedCount > 0) {
            delay(2000)

            // 70% 확률로 퀴즈가 뜨도록 설정
            if ((0..9).random() < 7) {
                quiz = QuizData.randomQuiz()
                showQuizOptions = true
            }
            tempFeedCount = 0
        }
    }

    LaunchedEffect(showAnswerBox) {
        if (showAnswerBox) {
            isButtonEnabled = false
            delay(3000)
            showAnswerBox = false
            isButtonEnabled = true
        }
    }

    LaunchedEffect(Unit) {
        if (UserDataHolder.goat_age == 0) {
            UserDataHolder.goat_age = GoatType.entries.random().id * 1000
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            UserDataHolder.hay_num = localHayNum
        }
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
                    UserDataHolder.goat_age = 0
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${feedPercentage.toInt()}%", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(15.dp))
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

        Spacer(modifier = Modifier.height(20.dp))

        // 염소 이미지 자리
        Box(
            modifier = Modifier
                .size(170.dp)
                .background(goatType.color),
            contentAlignment = Alignment.TopCenter
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.goat)
                    .decoderFactory(ImageDecoderDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 염소 종류 텍스트 표시
        Box(
            modifier = Modifier
                .background(Color(0xFFDABFB2), shape = RoundedCornerShape(8.dp)) // 배경과 모서리 설정
                .padding(horizontal = 16.dp, vertical = 8.dp) // 패딩 추가
        ) {
            Text(
                text = when (goatType) {
                    GoatType.BASIC -> "바바 염소"
                    GoatType.CULTURE1 -> "바리 염소"
                    GoatType.CULTURE2 -> "리리 염소"
                },
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // "여물 연속 횟수" 텍스트 표시
        Text(text = "X $tempFeedCount", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))

        // "보유 건초 수: n개" 텍스트 표시
        Text(
            text = "보유 건초 수: $localHayNum 개",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                if (localHayNum > 0 && feedCount < maxFeedCount && isButtonEnabled) {
                    UserDataHolder.goat_age = goatType.id * 1000 + (feedCount + 1)
                    localHayNum--
                    tempFeedCount++
                }

                if (feedCount + 1 == maxFeedCount) {
                    showDialog = true
                }
            },
            modifier = Modifier.padding(8.dp),
            enabled = isButtonEnabled && localHayNum > 0
        ) {
            Text(
                text = "먹이주기",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 퀴즈 상자 표시
        if (showQuizOptions && quiz != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = quiz!!.question,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 정답/오답 상자 표시
        if (showAnswerBox) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = answerText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // O, X 버튼 표시
        if (showQuizOptions && quiz != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        if (quiz!!.answer) {
                            answerText = "정답입니다!"
                        } else {
                            answerText = "틀렸습니다!"
                        }
                        showQuizOptions = false
                        showAnswerBox = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    Text("O")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (!quiz!!.answer) {
                            answerText = "정답입니다!"
                        } else {
                            answerText = "틀렸습니다!"
                        }
                        showQuizOptions = false
                        showAnswerBox = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    Text("X")
                }
            }
        }
    }
}

