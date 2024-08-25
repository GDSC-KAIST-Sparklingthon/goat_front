package com.example.sparklington

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun TabContent4() {
    Column(
        modifier = Modifier
            .fillMaxWidth(), // 가로 전체 채우기
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 "마이페이지" 텍스트
        Text(
            text = "마이페이지",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(), // 세로 전체 채우기
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // 회색 원 프로필 이미지 대체
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 사용자 이름
        Text(
            text = UserDataHolder.nickname ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 하단 연보라색 줄
        HorizontalDivider(
            color = Color(0xFF3F352C),
            thickness = 5.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        DonationCard(
            imageResId = R.drawable.four, // 여기에 업로드한 이미지를 사용
            organization = "그린피스",
            date = "2024년 7월 17일"
        )

        // 두 번째 기부 정보 카드
        DonationCard(
            imageResId = R.drawable.four, // 여기에 업로드한 이미지를 사용
            organization = "세이브더칠드런",
            date = "2024년 8월 20일"
        )
    }
}

@Composable
fun DonationHistory() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 첫 번째 기부 정보 카드
        DonationCard(
            imageResId = R.drawable.four, // 여기에 업로드한 이미지를 사용
            organization = "그린피스",
            date = "2024년 7월 17일"
        )

        // 두 번째 기부 정보 카드
        DonationCard(
            imageResId = R.drawable.four, // 여기에 업로드한 이미지를 사용
            organization = "세이브더칠드런",
            date = "2024년 8월 20일"
        )
    }
}

@Composable
fun DonationCard(imageResId: Int, organization: String, date: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFDDE8D4),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 원형 이미지
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 기부 단체와 날짜 텍스트
            Column {
                Text(
                    text = "기부단체: $organization",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "날짜: $date",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                    color = Color.DarkGray
                )
            }
        }
    }
}


