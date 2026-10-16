package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)

enum class SeniorTextScale(val multiplier: Float, val label: String) {
    NORMAL(1.0f, "Normal"),
    LARGE(1.2f, "Large (120%)"),
    EXTRA_LARGE(1.4f, "Extra Large (140%)")
}

fun Typography.scaled(multiplier: Float): Typography {
    if (multiplier == 1.0f) return this
    return Typography(
        headlineLarge = headlineLarge.copy(fontSize = (headlineLarge.fontSize.value * multiplier).sp, lineHeight = (headlineLarge.lineHeight.value * multiplier).sp),
        headlineMedium = headlineMedium.copy(fontSize = (headlineMedium.fontSize.value * multiplier).sp, lineHeight = (headlineMedium.lineHeight.value * multiplier).sp),
        headlineSmall = headlineSmall.copy(fontSize = (headlineSmall.fontSize.value * multiplier).sp, lineHeight = (headlineSmall.lineHeight.value * multiplier).sp),
        titleLarge = titleLarge.copy(fontSize = (titleLarge.fontSize.value * multiplier).sp, lineHeight = (titleLarge.lineHeight.value * multiplier).sp),
        titleMedium = titleMedium.copy(fontSize = (titleMedium.fontSize.value * multiplier).sp, lineHeight = (titleMedium.lineHeight.value * multiplier).sp),
        titleSmall = titleSmall.copy(fontSize = (titleSmall.fontSize.value * multiplier).sp, lineHeight = (titleSmall.lineHeight.value * multiplier).sp),
        bodyLarge = bodyLarge.copy(fontSize = (bodyLarge.fontSize.value * multiplier).sp, lineHeight = (bodyLarge.lineHeight.value * multiplier).sp),
        bodyMedium = bodyMedium.copy(fontSize = (bodyMedium.fontSize.value * multiplier).sp, lineHeight = (bodyMedium.lineHeight.value * multiplier).sp),
        labelLarge = labelLarge.copy(fontSize = (labelLarge.fontSize.value * multiplier).sp, lineHeight = (labelLarge.lineHeight.value * multiplier).sp)
    )
}
