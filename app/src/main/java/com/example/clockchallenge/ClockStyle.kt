package com.example.clockchallenge

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
    val secSize: Dp = 115.dp,
    val minSize: Dp = 110.dp,
    val hourSize: Dp = 105.dp,
    val secColor: Color = Color.Red,
    val minColor: Color = Color.Black,
    val hourColor: Color = Color.Black,
    val fiveStepLinesColor: Color = Color.Black,
    val normalLinesColor: Color = Color.LightGray,
    val circleRadius: Dp = 150.dp,
    val fiveStepLinesSize: Dp = 25.dp,
    val normalLinesSize: Dp = 15.dp,
    val shadowSize: Dp = 10.dp
)