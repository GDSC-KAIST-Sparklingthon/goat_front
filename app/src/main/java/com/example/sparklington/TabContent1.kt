package com.example.sparklington

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContent1(modifier: Modifier = Modifier, isRunningState: (Boolean) -> Unit) {
    var remainingTicks by rememberSaveable { mutableStateOf(0) }
    var grassIncreaseAmount by rememberSaveable { mutableStateOf(0) }
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var betGrass by rememberSaveable { mutableStateOf(0) } // Saveable로 유지
    var grassToGet by rememberSaveable { mutableStateOf(0) }
    val gridRows = 8
    val gridColumns = 8
    val maxGrassCount = gridRows * gridColumns
    val positions = remember { mutableStateListOf<Pair<Int, Int>>() }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        positions.clear()
        positions.add(Pair(1, 4))
        positions.add(Pair(7, 2))
        positions.add(Pair(4, 5))
    }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            isRunningState(true)
        } else {
            isRunningState(false)
        }

        while (isRunning) {
            kotlinx.coroutines.delay(1000L)
            if (remainingTicks > 0) {
                remainingTicks--
            } else {
                isRunning = false
                for (i in (1..grassIncreaseAmount)) {
                    var r: Int
                    var c: Int
                    do {
                        r = Random.nextInt(0, gridRows)
                        c = Random.nextInt(0, gridColumns)
                    } while (positions.contains(Pair(r, c)))
                    positions.add(Pair(r, c))
                }
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            FarmBottomSheet(scaffoldState, scope, gridRows, gridColumns, positions)
        },
        sheetPeekHeight = 56.dp,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Timer(remainingTicks)
            Text("획득 예정: ${betGrass + grassToGet}개") // betGrass를 표시
            TimerButtons(
                onStart = { isRunning = true },
                onPause = { isRunning = false },
                onSetTime = { showDialog = true }
            )

            if (showDialog) {
                TimeSettingDialog(
                    selectedHours = selectedHours,
                    onHoursChange = { selectedHours = it },
                    selectedMinutes = selectedMinutes,
                    onMinutesChange = { selectedMinutes = it },
                    betGrass = betGrass,
                    onBetGrassChange = { betGrass = it },
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        remainingTicks = selectedHours * 3600 + selectedMinutes * 60
                        grassIncreaseAmount = (selectedHours * 60 + selectedMinutes) / 30
                        val betTimeUnit = selectedMinutes / 30 + selectedHours * 2
                        grassToGet = ObtainingGrass(betGrass, betTimeUnit) // betGrass 업데이트
                        isRunning = false
                        showDialog = false
                    }
                )
            }
        }
    }
}

fun ObtainingGrass(betGrass: Int, betTimeUnit: Int): Int {
    return (betGrass.toFloat() * (betTimeUnit * 0.1)).toInt()
}
