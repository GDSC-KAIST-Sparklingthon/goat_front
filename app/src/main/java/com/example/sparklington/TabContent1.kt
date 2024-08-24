package com.example.sparklington

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContent1(modifier: Modifier = Modifier, isRunningState: (Boolean) -> Unit) {
    var remainingTicks by rememberSaveable { mutableStateOf(0) }
    var grassIncreaseAmount by rememberSaveable { mutableStateOf(0) }
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showExitConfirmation by remember { mutableStateOf(false) }
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var betGrass by rememberSaveable { mutableStateOf(0) }
    var grassToGet by rememberSaveable { mutableStateOf(0) }
    var currentGrass by rememberSaveable { mutableStateOf(100) }
    val gridRows = 8
    val gridColumns = 8
    val maxGrassCount = gridRows * gridColumns
    val positions = remember { mutableStateListOf<Pair<Int, Int>>() }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url("https://lottie.host/f01dc1d7-8155-4554-a269-7fa00788c0ba/gpoRgALuCo.json")
    )

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

    BackHandler(isRunning) {
        showExitConfirmation = true
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
            Text("현재 잔디: $currentGrass 개")
            Text("획득 예정: ${betGrass + grassToGet}개")
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
                            currentGrass -= betGrass
                            currentGrass = max(currentGrass, 0)
                        }
                        showDialog = false
                    }
                )
            }

            // 경고창 표시
            if (showExitConfirmation) {
                AlertDialog(
                    onDismissRequest = { showExitConfirmation = false },
                    title = { Text("경고") },
                    text = { Text("정말 앱을 종료하시겠습니까? 잔디를 모두 잃게 됩니다.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showExitConfirmation = false
                            isRunning = false
                        }) {
                            Text("종료")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showExitConfirmation = false }) {
                            Text("취소")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(100.dp)) // TimerButtons와 LottieAnimation 사이에 30dp 간격 추가

            if (composition != null) {
                // GIF 이미지 (Lottie 애니메이션)
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // 원하는 높이로 조절
                )
            } else {
                Text("Loading animation...", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

fun ObtainingGrass(betGrass: Int, betTimeUnit: Int): Int {
    return (betGrass.toFloat() * (betTimeUnit * 0.1)).toInt()
}
