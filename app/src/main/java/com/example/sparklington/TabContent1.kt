package com.example.sparklington

import android.service.autofill.UserData
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
    var showCongrats by remember { mutableStateOf(false) } // 축하 메시지 상태 변수
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var betGrass by rememberSaveable { mutableStateOf(0) }
    var grassToGet by rememberSaveable { mutableStateOf(0) }
    var currentHay by rememberSaveable { mutableStateOf(0) }
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
        currentHay = UserDataHolder.hay_num
        positions.clear()
        positions.addAll(UserDataHolder.garden_array)
    }
    LaunchedEffect(currentHay) {
        UserDataHolder.hay_num = currentHay
    }
    LaunchedEffect(positions) {
        UserDataHolder.garden_array = positions.toList()
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
                for (i in (1..grassIncreaseAmount)) {
                    var r: Int
                    var c: Int
                    do {
                        r = Random.nextInt(0, gridRows)
                        c = Random.nextInt(0, gridColumns)
                    } while (positions.contains(Pair(r, c)))
                    positions.add(Pair(r, c))
                }

                showCongrats = true
            }
        }
    }

    BackHandler(isRunning) {
        showExitConfirmation = true
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            FarmBottomSheet(
                scaffoldState,
                scope,
                gridRows,
                gridColumns,
                positions,
                onGrassCollected = { position ->
                    positions.remove(position)
                    currentHay += 1
                })
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
            Text("현재 잔디: ${positions.count()} 개")
            Text("현재 건초: $currentHay 개")
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
                        if (it <= positions.count()) {
                            betGrass = it
                        } else {
                            betGrass = positions.count()
                        }
                    },
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        if (betGrass <= positions.count()) {
                            remainingTicks = selectedHours * 3600 + selectedMinutes * 60
                            val betTimeUnit = selectedMinutes / 30 + selectedHours * 2
                            grassToGet = ObtainingGrass(betGrass, betTimeUnit)
                            grassIncreaseAmount = grassToGet + betGrass
                        }
                        showDialog = false
                    }
                )
            }

            if (showCongrats) {
                AlertDialog(
                    onDismissRequest = { showCongrats = false },
                    title = { Text("목표 달성 완료") },
                    text = { Text("축하합니다! ${betGrass + grassToGet}포기의 잔디를 획득했어요.") },
                    confirmButton = {
                        TextButton(onClick = { showCongrats = false }) {
                            Text("염소 먹이러 가기")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showCongrats = false }) {
                            Text("닫기")
                        }
                    }
                )
            }

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
                Text(
                    "Loading animation...",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

fun ObtainingGrass(betGrass: Int, betTimeUnit: Int): Int {
    return (betGrass.toFloat() * (betTimeUnit * 0.1)).toInt()
}
