package com.example.sparklington

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sparklington.data.getDonationSites

data class DonationSite(
    val krName: String,
    val engName: String,
    val url: String,
    val imageResId: Int,
    val imageResSam: Int,
    val description: String,
    val category: String
)

@Composable
fun TabContent3() {
    val donationSites = getDonationSites()


    // 중복을 제거한 카테고리 리스트 생성
    val uniqueCategories = donationSites.map { it.category }.distinct()

    // 상태 변수를 사용해 현재 선택된 카테고리를 추적
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(//
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 연보라색 네모와 "기부와 후원" 텍스트 추가 (가운데 정렬)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE1BEE7)) // 연보라색 배경
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "기부와 후원",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // 카테고리별 필터 버튼을 생성
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uniqueCategories) { category ->
                Button(
                    onClick = { selectedCategory = category },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category) Color(0xFF6200EA) else Color.LightGray
                    )
                ) {
                    Text(text = category)
                }
            }

            item {
                Button(
                    onClick = { selectedCategory = null },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == null) Color(0xFF6200EA) else Color.LightGray
                    )
                ) {
                    Text(text = "전체")
                }
            }
        }

        // 선택된 카테고리에 따라 필터링된 기부 단체 목록을 표시
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(donationSites.filter { it.category == selectedCategory || selectedCategory == null }) { site ->
                DonationSiteItem(site)
            }
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
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (site.imageResId != 0) {
            Image(
                painter = painterResource(id = site.imageResId),
                contentDescription = site.engName,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
            )
        }

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
        if (site.imageResSam != 0) {
            Image(
                painter = painterResource(id = site.imageResSam),
                contentDescription = site.engName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = site.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ColumnCardAlert(site: DonationSite, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = site.krName) },
        text = {
            AlertCard(site)
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(site.url))
                    context.startActivity(intent)
                }) {
                    Text("직접 후원하기")
                }

                TextButton(onClick = { /* 기부 로직 추가 */ }) {
                    Text("지금 후원하기")
                }

                TextButton(onClick = { onDismiss() }) {
                    Text("취소")
                }
            }
        }
    )
}


