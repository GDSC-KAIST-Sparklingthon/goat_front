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
    var betGrass by rememberSaveable { mutableStateOf(0) }
    var grassToGet by rememberSaveable { mutableStateOf(0) }
    var currentGrass by rememberSaveable { mutableStateOf(100) } // 초기 잔디 수
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

    // 앱을 나갈 때의 처리
    DisposableEffect(isRunning) {
        onDispose {
            if (isRunning) {
                // 앱을 나가면 배팅한 잔디를 잃음
                currentGrass -= betGrass
            }
        }
    }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            isRunningState(true)
        } else {
            isRunningState(false)
        }

        while (isRunning) {
            delay(1000L)
            if (remainingTicks > 0) {
                remainingTicks--
            } else {
                isRunning = false
                currentGrass += betGrass + grassToGet // 성공적으로 기다린 경우 잔디 추가
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
            Text("현재 잔디: $currentGrass 개") // 현재 잔디 수 표시
            Text("획득 예정: ${betGrass + grassToGet}개") // betGrass를 표시
            TimerButtons(
                onStart = {
                    isRunning = true
                    currentGrass -= betGrass
                },
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
                    onBetGrassChange = {
                        if (it <= currentGrass) {
                            betGrass = it
                        } else {
                            betGrass = currentGrass
                        }
                    },
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        if (betGrass <= currentGrass) {
                            remainingTicks = selectedHours * 3600 + selectedMinutes * 60
                            val betTimeUnit = selectedMinutes / 30 + selectedHours * 2
                            grassToGet = ObtainingGrass(betGrass, betTimeUnit)
                            grassIncreaseAmount = grassToGet + betGrass
                            isRunning = true
                        }
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
