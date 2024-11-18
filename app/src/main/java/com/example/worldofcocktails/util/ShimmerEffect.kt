package com.example.worldofcocktails.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.worldofcocktails.ui.theme.Gray
import com.example.worldofcocktails.ui.theme.Orange

@Composable
fun LoadingShimmerEffect() {
    Column(modifier = Modifier.fillMaxSize()) {
        repeat(7) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.shimmerHeight)
                    .padding(Dimens.padding)
                    .shimmerEffect()
            )
        }
    }
}

private fun Modifier.shimmerEffect(
    widthOfShadowBrush: Int = 300,
    endOfOffsetY: Float = 80f,
    durationMillis: Int = 1000,
): Modifier = composed {
    val shimmerColors = listOf(
        Color.Transparent,
        Color.Transparent,
        Orange,
        Color.White,
        Gray,
        Color.Transparent,
        Color.Transparent,
    )

    val transition = rememberInfiniteTransition(label = "shimmerLoadingAnimation")

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val width = with(density) { configuration.screenWidthDp.dp.toPx() }

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = width + 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )
    this.drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = endOfOffsetY),

                )
        )
    }
}