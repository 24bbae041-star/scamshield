package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ButterYellow
import com.example.ui.theme.ButterYellowDark
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.Mint
import com.example.ui.theme.MintDark
import com.example.ui.theme.Peach
import com.example.ui.theme.PeachDark
import com.example.ui.theme.WarningRose

enum class StickerType {
    SHIELD,
    PHONE,
    HEART,
    MAGNIFYING_GLASS,
    COIN,
    STAR,
    WARNING,
    BELL
}

@Composable
fun CuteStickerBadge(
    type: StickerType,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val (bgColor, iconColor, icon) = when (type) {
        StickerType.SHIELD -> Triple(Lavender, DeepPurple, Icons.Default.Security)
        StickerType.PHONE -> Triple(Mint, MintDark, Icons.Default.Call)
        StickerType.HEART -> Triple(Peach, PeachDark, Icons.Default.Favorite)
        StickerType.MAGNIFYING_GLASS -> Triple(ButterYellow, ButterYellowDark, Icons.Default.Search)
        StickerType.COIN -> Triple(ButterYellow, DarkInk, Icons.Default.CreditCard)
        StickerType.STAR -> Triple(ButterYellow, DarkInk, Icons.Default.Star)
        StickerType.WARNING -> Triple(WarningRose.copy(alpha = 0.2f), WarningRose, Icons.Default.Security)
        StickerType.BELL -> Triple(Lavender, DeepPurple, Icons.Default.Notifications)
    }

    Box(
        modifier = modifier
            .size(size)
            .shadow(3.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(2.dp, Color.White, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(size * 0.55f)
        )
    }
}

@Composable
fun CuteEmojiSticker(
    emoji: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    bgColor: Color = Lavender
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(2.dp, CircleShape)
            .clip(CircleShape)
            .background(bgColor)
            .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = (size.value * 0.45f).sp
        )
    }
}
