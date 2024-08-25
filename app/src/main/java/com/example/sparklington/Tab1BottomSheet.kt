package com.example.sparklington

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    gridRows: Int,
    gridColumns: Int,
    positions: List<Pair<Int, Int>>,
    onGrassCollected: (Pair<Int, Int>) -> Unit
) {
    val grassImage = painterResource(id = R.drawable.grass)
    val maxGrassCount = gridRows * gridColumns

    val scrollState = rememberScrollState()

    // 스크롤 상태에 따라 알파 값을 조정하여 색상을 변경
    val scrollMaxValue = 600 // 임의의 최대 스크롤 값
    val alpha = (scrollState.value.toFloat() / scrollMaxValue).coerceIn(0f, 1f)
    val backgroundColor = Color(0xFF1C3506).copy(alpha = alpha)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp, max = 600.dp)
            .padding(16.dp)
            .verticalScroll(scrollState)
            .background(backgroundColor), // 스크롤 상태에 따른 배경색
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Grid layout
        for (row in 0 until gridRows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (column in 0 until gridColumns) {
                    val position = Pair(row, column)
                    val hasGrass = positions.contains(position)
                    GrassCell(hasGrass, grassImage) {
                        if (hasGrass) {
                            onGrassCollected(position)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val currentGrassCount = positions.size
        val progress = currentGrassCount.toFloat() / maxGrassCount

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "$currentGrassCount / $maxGrassCount",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun GrassCell(hasGrass: Boolean, painter: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(3.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (hasGrass) {
            Image(painter = painter, contentDescription = "Grass")
        }
    }
}
