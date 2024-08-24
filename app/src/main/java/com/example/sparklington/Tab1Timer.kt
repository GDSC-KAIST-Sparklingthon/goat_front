package com.example.sparklington

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun Timer(remainingTicks: Int) {
    val hours = String.format("%02d", remainingTicks / 3600)
    val minutes = String.format("%02d", (remainingTicks % 3600) / 60)
    val seconds = String.format("%02d", remainingTicks % 60)

    Text(
        text = "$hours : $minutes : $seconds",
        fontSize = 48.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TimerButtons(onStart: () -> Unit, onPause: () -> Unit, onSetTime: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onStart) {
            Text(text = "집중 시작")
        }
        Button(onClick = onSetTime) {
            Text(text = "시간 설정")
        }
        Button(onClick = onPause) {
            Text(text = "일시 정지")
        }
    }
}

@Composable
fun TimeSettingDialog(
    selectedHours: Int,
    onHoursChange: (Int) -> Unit,
    selectedMinutes: Int,
    onMinutesChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "타이머 설정") },
        text = {
            Row {
                NumberPickerView(
                    value = selectedHours,
                    range = 0..23,
                    onValueChange = onHoursChange
                )
                MinutePickerView(
                    value = selectedMinutes,
                    minuteValues = (0..11).map { it * 5 }.toTypedArray(),
                    onValueChange = onMinutesChange
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("설정")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun NumberPickerView(value: Int, range: IntRange, onValueChange: (Int) -> Unit) {
    val context = LocalContext.current
    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                minValue = range.first
                maxValue = range.last
                this.value = value
                setOnValueChangedListener { _, _, newVal ->
                    onValueChange(newVal)
                }
            }
        },
        update = { view ->
            view.value = value
        }
    )
}

@Composable
fun MinutePickerView(value: Int, minuteValues: Array<Int>, onValueChange: (Int) -> Unit) {
    val context = LocalContext.current
    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                minValue = 0
                maxValue = minuteValues.size - 1
                displayedValues = minuteValues.map { it.toString() }.toTypedArray()
                this.value = minuteValues.indexOf(value)
                setOnValueChangedListener { _, _, newVal ->
                    onValueChange(minuteValues[newVal])
                }
            }
        },
        update = { view ->
            view.value = minuteValues.indexOf(value)
        }
    )
}