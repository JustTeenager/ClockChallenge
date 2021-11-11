package com.example.clockchallenge

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
@Preview
fun Clock(
    modifier: Modifier = Modifier,
    clockStyle: ClockStyle = ClockStyle(),
    initSeconds: Float = 50f,
    initMinutes: Float = 30f,
    initHours: Float = 13f,
    onTimeChanged: () -> Unit = { }
) {
    var seconds by remember {
        mutableStateOf(initSeconds)
    }

    var minutes by remember {
        mutableStateOf(initMinutes)
    }

    var hours by remember {
        mutableStateOf(initHours)
    }

    LaunchedEffect(key1 = seconds) {
        delay(50)
        minutes += 1f/60
        hours += 1f / (60 * 12)
        seconds += 1f
    }


    Canvas(modifier = modifier.fillMaxSize()) {
        val radius = clockStyle.circleRadius.toPx()
        val shadowSize = clockStyle.shadowSize.toPx()
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                radius,
                Paint().apply {
                    color = Color.WHITE
                    style = Paint.Style.FILL
                    setShadowLayer(
                        shadowSize,
                        0f,
                        0f,
                        Color.argb(75, 0, 0, 0)
                    )
                }
            )
        }

            for (i in 0..60) {
                val angleInRad = calculateRad(i.toFloat())
                val lineLength = when (i % 5) {
                    0 -> clockStyle.fiveStepLinesSize
                    else -> clockStyle.normalLinesSize
                }.toPx()
                val lineColor = when (i % 5) {
                    0 -> clockStyle.fiveStepLinesColor
                    else -> clockStyle.normalLinesColor
                }

                val startPosition = Offset(
                    (radius * cos(angleInRad)) + center.x,
                    (radius * sin(angleInRad)) + center.y
                )
                val endPosition = Offset(
                    ( (radius - lineLength) * cos(angleInRad) ) + center.x,
                    ( (radius - lineLength) * sin(angleInRad) ) + center.y
                )

                drawLine(
                    color = lineColor,
                    start = startPosition,
                    end = endPosition,
                    strokeWidth = 1.dp.toPx()
                )
            }

            val secRadius = clockStyle.secSize.toPx()
            val secColor = clockStyle.secColor
            val secAngleInRad = calculateRad(seconds)
            val secEndOffset = Offset(
                x = secRadius * cos(secAngleInRad) + center.x,
                y = secRadius * sin(secAngleInRad) + center.y
            )
            drawLine(
                color = secColor,
                start = center,
                end = secEndOffset,
                strokeWidth = 3.dp.toPx()
            )

            val minRadius = clockStyle.minSize.toPx()
            val minColor = clockStyle.minColor
            val minAngleInRad = calculateRad(minutes)
            val minEndOffset = Offset(
                x = minRadius * cos(minAngleInRad) + center.x,
                y = minRadius * sin(minAngleInRad) + center.y
            )
            drawLine(
                color = minColor,
                start = center,
                end = minEndOffset,
                strokeWidth = 3.dp.toPx()
            )

            val hourRadius = clockStyle.hourSize.toPx()
            val hourColor = clockStyle.hourColor
            val hourAngleInRad = calculateRad(hours)
            val hourEndOffset = Offset(
                x = hourRadius * cos(hourAngleInRad) + center.x,
                y = hourRadius * sin(hourAngleInRad) + center.y
            )
            drawLine(
                color = hourColor,
                start = center,
                end = hourEndOffset,
                strokeWidth = 5.dp.toPx()
            )
    }
}

private fun calculateRad(i: Float): Float {
    return (i * (360/60) - 90) * (PI / 180).toFloat()
}