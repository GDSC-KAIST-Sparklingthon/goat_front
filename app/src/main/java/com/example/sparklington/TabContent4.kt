package com.example.sparklington

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource

@Composable
fun TabContent4() {
    Column(
        modifier = Modifier
            .fillMaxWidth() // 가로 전체 채우기
            .background(Color(0xFFECE4FF)) // 연보라색 배경
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 "마이페이지" 텍스트
        Text(
            text = "마이페이지",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(), // 세로 전체 채우기
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // 회색 원 프로필 이미지 대체
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        ){
            Image(painter = painterResource(id = R.drawable.avatar),
                contentDescription = "")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 사용자 이름
        Text(
            text = "정지수 님",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 하단 연보라색 줄
        HorizontalDivider(
            color = Color(0xFFECE4FF),
            thickness = 4.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}



