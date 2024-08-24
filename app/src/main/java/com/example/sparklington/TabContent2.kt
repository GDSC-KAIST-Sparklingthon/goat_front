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

@Composable
fun GoatTabContent(onDonateClicked: () -> Unit) {
    var feedCount by remember { mutableStateOf(0) }
    var tempFeedCount by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val maxFeedCount = 100
    val feedPercentage = (feedCount.toFloat() / maxFeedCount) * 100

    val scope = rememberCoroutineScope()

    // 연타 후 일정 시간 동안 횟수를 유지한 뒤 초기화하는 효과
    LaunchedEffect(tempFeedCount) {
        if (tempFeedCount > 0) {
            delay(2000)  // 2초 동안 횟수 유지
            tempFeedCount = 0  // 초기화
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
        // 염소 이름과 상태 표시
        Text(
            text = "염소",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 염소가 먹은 퍼센트 표시
        Text(text = "${feedPercentage.toInt()}%", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // 염소가 먹은 양을 시각화 (블럭)
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

        // 염소 이미지 자리 (임시 네모)
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 먹이주기 버튼
        Button(
            onClick = {
                if (feedCount < maxFeedCount) {
                    feedCount++
                    tempFeedCount++
                }

                if (feedCount == maxFeedCount) {
                    showDialog = true
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "먹이주기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 먹이 준 횟수 표시 (연타 횟수)
        Text(text = "X $tempFeedCount", fontSize = 24.sp)
    }
}