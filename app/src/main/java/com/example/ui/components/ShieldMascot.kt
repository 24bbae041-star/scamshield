package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ButterYellow
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.Mint
import com.example.ui.theme.Peach

@Composable
fun CuteShieldMascot(
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    showSparkles: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sparkle_anim")
    val sparkleScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_scale"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height

            // 1. Draw Cute Purple Shield Body
            val shieldPath = Path().apply {
                moveTo(w * 0.18f, h * 0.15f)
                // Top curve
                quadraticTo(w * 0.5f, h * 0.08f, w * 0.82f, h * 0.15f)
                // Right side downwards
                quadraticTo(w * 0.86f, h * 0.55f, w * 0.5f, h * 0.92f)
                // Left side upwards
                quadraticTo(w * 0.14f, h * 0.55f, w * 0.18f, h * 0.15f)
                close()
            }

            // Shield Gradient
            val shieldBrush = Brush.verticalGradient(
                colors = listOf(Lavender, DeepPurple),
                startY = 0f,
                endY = h
            )
            drawPath(shieldPath, brush = shieldBrush)

            // Shield Border Stroke in Soft Mint
            drawPath(
                shieldPath,
                color = Mint,
                style = Stroke(width = w * 0.045f, cap = StrokeCap.Round)
            )

            // Mint Shield Emblem Accent on Top
            val emblemPath = Path().apply {
                moveTo(w * 0.42f, h * 0.22f)
                lineTo(w * 0.58f, h * 0.22f)
                lineTo(w * 0.5f, h * 0.30f)
                close()
            }
            drawPath(emblemPath, color = Mint)

            // 2. Cute Happy Eyes
            val eyeRadius = w * 0.055f
            val eyeLeft = Offset(w * 0.38f, h * 0.44f)
            val eyeRight = Offset(w * 0.62f, h * 0.44f)

            // Eyes
            drawCircle(color = Color(0xFF1F1836), radius = eyeRadius, center = eyeLeft)
            drawCircle(color = Color(0xFF1F1836), radius = eyeRadius, center = eyeRight)

            // Eye sparkle highlights
            drawCircle(color = Color.White, radius = eyeRadius * 0.45f, center = Offset(eyeLeft.x - eyeRadius * 0.2f, eyeLeft.y - eyeRadius * 0.2f))
            drawCircle(color = Color.White, radius = eyeRadius * 0.45f, center = Offset(eyeRight.x - eyeRadius * 0.2f, eyeRight.y - eyeRadius * 0.2f))

            // 3. Cheerful Rosy Cheeks
            drawCircle(color = Peach.copy(alpha = 0.85f), radius = eyeRadius * 0.9f, center = Offset(w * 0.29f, h * 0.52f))
            drawCircle(color = Peach.copy(alpha = 0.85f), radius = eyeRadius * 0.9f, center = Offset(w * 0.71f, h * 0.52f))

            // 4. Cheerful Smile
            val smilePath = Path().apply {
                moveTo(w * 0.43f, h * 0.54f)
                quadraticTo(w * 0.5f, h * 0.63f, w * 0.57f, h * 0.54f)
            }
            drawPath(
                smilePath,
                color = Color(0xFF1F1836),
                style = Stroke(width = w * 0.035f, cap = StrokeCap.Round)
            )

            // 5. Sparkles around the Shield
            if (showSparkles) {
                // Top-right Sparkle Star (Yellow)
                val trX = w * 0.88f
                val trY = h * 0.18f
                drawStar(Offset(trX, trY), w * 0.09f * sparkleScale, ButterYellow)

                // Bottom-left Sparkle Star (Mint)
                val blX = w * 0.12f
                val blY = h * 0.72f
                drawStar(Offset(blX, blY), w * 0.07f * sparkleScale, Mint)

                // Top-left small sparkle dot
                drawCircle(color = Color.White, radius = w * 0.025f, center = Offset(w * 0.18f, h * 0.12f))
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(
    center: Offset,
    radius: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        quadraticTo(center.x, center.y, center.x + radius, center.y)
        quadraticTo(center.x, center.y, center.x, center.y + radius)
        quadraticTo(center.x, center.y, center.x - radius, center.y)
        quadraticTo(center.x, center.y, center.x, center.y - radius)
        close()
    }
    drawPath(path, color = color)
}
