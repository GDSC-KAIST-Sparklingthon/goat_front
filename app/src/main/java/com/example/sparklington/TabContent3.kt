package com.example.sparklington

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DonationSite(
    val krName: String,
    val engName: String,
    val url: String,
    val imageResId: Int,
    val description: String,
    val category: String
)

@Composable
fun TabContent3() {
    val donationSites = listOf(
        DonationSite(
            "한국컴패션",
            "COMPASSION",
            "https://www.compassionkorea.org",
            R.drawable.grass,
            "한국의 전쟁고아를 돕기 위해 시작한 컴패션은 현재 전세계 29개국의 가난한 어린이들을 후원하여 자립이 가능 성인이 될 때 까지 전인적으로 양육하는 국제어린이양육기구입니다.",
            "아동"
        ),
        DonationSite(
            "적십자사",
            "Red Cross",
            "https://www.redcross.org",
            R.drawable.grass,
            "재난 구호, 생명 구조 활동",
            "보건"
        ),
        DonationSite(
            "유니세프",
            "UNICEF",
            "https://www.unicef.org",
            R.drawable.grass,
            "세계 아동 권리 보호 및 복지 활동",
            "아동"
        ),
        DonationSite(
            "그린피스",
            "Green Peace",
            "https://www.greenpeace.org",
            R.drawable.grass,
            "국제 환경 보호 단체로서 환경 문제 해결을 위한 활동을 합니다.",
            "환경"
        ),
        DonationSite(
            "월드비전",
            "World Vision",
            "https://www.worldvision.org",
            R.drawable.grass,
            "세계적인 구호 개발 기구로서 전 세계 아동, 가정 및 지역사회를 돕습니다.",
            "아동"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(donationSites) { site ->
            DonationSiteItem(site)
        }
    }
}

@Composable
fun DonationSiteItem(site: DonationSite) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            }
            .padding(vertical = 8.dp), // 리스트 간격
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = site.imageResId),
            contentDescription = site.engName,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 16.dp) // 이미지와 텍스트 사이 간격
        )

        Text(text = site.krName, fontSize = 20.sp)
    }

    if (showDialog) {
        ColumnCardAlert(site) {
            showDialog = false
        }
    }
}

@Composable
fun AlertCard(site: DonationSite) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = site.imageResId),
            contentDescription = site.engName,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = site.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ColumnCardAlert(site: DonationSite, onDismiss: () -> Unit) {
    val context = LocalContext.current  // Get the context within a @Composable function

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = site.krName) },
        text = {
            AlertCard(site)
        },
        confirmButton = {
            // Use a Row to horizontally arrange buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Evenly space buttons
            ) {
                TextButton(onClick = {
                    // 직접 후원하기 버튼 클릭 시 해당 URL로 리다이렉트
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(site.url))
                    context.startActivity(intent)
                }) {
                    Text("직접 후원하기")
                }

                TextButton(onClick = { /* ToDo: Add Donation logic when this button is pressed. */ }) {
                    Text("지금 후원하기")
                }

                TextButton(onClick = { onDismiss() }) {
                    Text("취소")
                }
            }
        }
    )
}
