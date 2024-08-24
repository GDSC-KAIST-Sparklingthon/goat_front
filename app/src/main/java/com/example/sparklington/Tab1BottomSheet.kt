package com.example.sparklington

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp, max = 600.dp)
            .padding(16.dp)
            .verticalScroll(scrollState),
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
            .size(30.dp)
            .padding(2.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (hasGrass) {
            Image(painter = painter, contentDescription = "Grass")
        }
    }
}
