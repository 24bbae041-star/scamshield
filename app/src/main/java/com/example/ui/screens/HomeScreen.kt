package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Localization
import com.example.data.ScamDatabase
import com.example.model.RiskLevel
import com.example.model.ScanRecord
import com.example.ui.AppScreen
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteEmojiSticker
import com.example.ui.components.CuteShieldMascot
import com.example.ui.components.CuteStickerBadge
import com.example.ui.components.GlowingSosButton
import com.example.ui.components.RiskBadge
import com.example.ui.components.SeniorCard
import com.example.ui.components.StickerType
import com.example.ui.theme.ButterYellow
import com.example.ui.theme.ButterYellowContainer
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.Mint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintDark
import com.example.ui.theme.Peach
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.WarningRose

@Composable
fun HomeScreen(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val scanHistory by viewModel.scanHistory.collectAsState()
    val trustedContacts by viewModel.trustedContacts.collectAsState()
    val dailyTipIdx by viewModel.dailyTipIndex.collectAsState()
    val completedQuizzes by viewModel.completedQuizzes.collectAsState()

    val currentTip = ScamDatabase.SAFETY_TIPS[dailyTipIdx]
    val primaryContact = trustedContacts.firstOrNull { it.isPrimary } ?: trustedContacts.firstOrNull()

    val totalScans = scanHistory.size
    val blockedThreats = scanHistory.count { it.riskLevel == RiskLevel.HIGH_RISK.name }
    val lessonsCompleted = completedQuizzes.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Header with Mascot Greeting & Glowing SOS
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    CuteShieldMascot(size = 54.dp, showSparkles = true)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = Localization.get("greeting", currentLang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = Localization.get("tagline", currentLang),
                            style = MaterialTheme.typography.bodyMedium,
                            color = DeepPurple
                        )
                    }
                }
                GlowingSosButton(
                    onClick = { viewModel.navigateTo(AppScreen.EMERGENCY_SOS) }
                )
            }
        }

        // 2. Daily Safety Tip Card
        item {
            SeniorCard(
                backgroundColor = ButterYellowContainer,
                borderColor = ButterYellow
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CuteStickerBadge(type = StickerType.STAR, size = 36.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = Localization.get("daily_safety_tip", currentLang),
                                style = MaterialTheme.typography.labelLarge.copy(color = DarkInk)
                            )
                        }
                        Row {
                            IconButton(
                                onClick = { viewModel.speakText(tts, "${currentTip.title}. ${currentTip.shortTip} ${currentTip.details}") },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Speak tip aloud",
                                    tint = DarkInk
                                )
                            }
                            IconButton(
                                onClick = { viewModel.nextDailyTip() },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Next tip",
                                    tint = DarkInk
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentTip.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentTip.shortTip,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF6B4800)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentTip.details,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkInk
                    )
                }
            }
        }

        // 3. Four Core Senior Quick Action Cards
        item {
            Text(
                text = "Quick Safety Actions",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Card 1: Scan a Screenshot
                ActionCard(
                    title = Localization.get("action_scan_screenshot", currentLang),
                    subtitle = "Pick from gallery or photo of SMS / WhatsApp notice",
                    stickerType = StickerType.SHIELD,
                    bgGradient = listOf(LavenderContainer, Color(0xFFF6F0FF)),
                    borderColor = Lavender,
                    testTag = "home_action_scan_screenshot",
                    onClick = { viewModel.navigateTo(AppScreen.SCANNER) }
                )

                // Card 2: Check a Suspicious Message
                ActionCard(
                    title = Localization.get("action_check_message", currentLang),
                    subtitle = "Paste message text, sender number, or link",
                    stickerType = StickerType.MAGNIFYING_GLASS,
                    bgGradient = listOf(ButterYellowContainer, Color(0xFFFFFDF5)),
                    borderColor = ButterYellow,
                    testTag = "home_action_check_message",
                    onClick = { viewModel.navigateTo(AppScreen.SCANNER) }
                )

                // Card 3: Pause Before You Pay 🛑
                ActionCard(
                    title = Localization.get("action_pause_pay", currentLang),
                    subtitle = "Wait! Let's check before you pay with Google Pay/UPI",
                    stickerType = StickerType.COIN,
                    bgGradient = listOf(PeachContainer, Color(0xFFFFF8F5)),
                    borderColor = Peach,
                    testTag = "home_action_pause_pay",
                    onClick = { viewModel.navigateTo(AppScreen.PAUSE_PAY) }
                )

                // Card 4: Talk to Scan Shield
                ActionCard(
                    title = Localization.get("action_talk_shield", currentLang),
                    subtitle = "Friendly voice assistant answering your digital safety questions",
                    stickerType = StickerType.PHONE,
                    bgGradient = listOf(MintContainer, Color(0xFFF3FAF7)),
                    borderColor = Mint,
                    testTag = "home_action_talk_shield",
                    onClick = { viewModel.openVoiceAssistant() }
                )
            }
        }

        // 4. Trusted Family Contact Card
        item {
            SeniorCard(
                backgroundColor = Color.White,
                borderColor = Lavender
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CuteEmojiSticker(
                                emoji = primaryContact?.avatarEmoji ?: "👨‍💼",
                                size = 42.dp,
                                bgColor = LavenderContainer
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = Localization.get("trusted_family", currentLang),
                                    style = MaterialTheme.typography.labelLarge.copy(color = SubtitleInk)
                                )
                                Text(
                                    text = primaryContact?.name ?: "Add Trusted Family Member",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = DarkInk
                                )
                            }
                        }

                        if (primaryContact != null) {
                            Surface(
                                onClick = {
                                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${primaryContact.phoneNumber.replace(" ", "")}")
                                    }
                                    context.startActivity(dialIntent)
                                },
                                shape = RoundedCornerShape(16.dp),
                                color = Mint,
                                modifier = Modifier.testTag("call_trusted_contact_button")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Call,
                                        contentDescription = "Call",
                                        tint = MintDark,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Call",
                                        style = MaterialTheme.typography.labelLarge.copy(color = MintDark)
                                    )
                                }
                            }
                        } else {
                            IconButton(onClick = { viewModel.navigateTo(AppScreen.SETTINGS) }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Add contact",
                                    tint = DeepPurple
                                )
                            }
                        }
                    }

                    if (primaryContact != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Phone: ${primaryContact.phoneNumber} • ${primaryContact.notes}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SubtitleInk
                        )
                    }
                }
            }
        }

        // 5. Senior Safety Summary Metrics
        item {
            SeniorCard(
                backgroundColor = Color(0xFFF9F7FF),
                borderColor = Color(0xFFE2DCF0)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Safety Summary",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem(title = "Scans Checked", value = "$totalScans", emoji = "🔍")
                        MetricItem(title = "Threats Blocked", value = "$blockedThreats", emoji = "🛡️", valueColor = WarningRose)
                        MetricItem(title = "Quizzes Mastered", value = "$lessonsCompleted / 12", emoji = "🏅", valueColor = DeepPurple)
                    }
                }
            }
        }

        // 6. Recent Scan History List
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = Localization.get("recent_scans", currentLang),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (scanHistory.isNotEmpty()) {
                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.labelLarge.copy(color = DeepPurple),
                        modifier = Modifier.clickable { viewModel.navigateTo(AppScreen.SCANNER) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (scanHistory.isEmpty()) {
            item {
                SeniorCard(
                    backgroundColor = Color.White,
                    borderColor = Color(0xFFE5E0F2)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CuteShieldMascot(size = 64.dp, showSparkles = false)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No scans yet",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                        Text(
                            text = "Upload a screenshot or check a message above to verify.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SubtitleInk
                        )
                    }
                }
            }
        } else {
            items(scanHistory.take(4)) { record ->
                HistoryScanCard(
                    record = record,
                    onClick = { viewModel.showScanDetails(record) }
                )
            }
        }

        // 7. Learn & Stay Safe Promo Card
        item {
            SeniorCard(
                backgroundColor = LavenderContainer,
                borderColor = Lavender,
                onClick = { viewModel.navigateTo(AppScreen.LEARN) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CuteStickerBadge(type = StickerType.HEART, size = 48.dp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "12 Interactive Safety Quizzes",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                        Text(
                            text = "Earn badges like Pension Guardian & UPI Master!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SubtitleInk
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Go",
                        tint = DeepPurple
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    stickerType: StickerType,
    bgGradient: List<Color>,
    borderColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    SeniorCard(
        borderColor = borderColor,
        onClick = onClick,
        modifier = Modifier.testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(bgGradient))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CuteStickerBadge(type = stickerType, size = 48.dp)
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SubtitleInk
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = DarkInk,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun MetricItem(
    title: String,
    value: String,
    emoji: String,
    valueColor: Color = DarkInk
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
            color = valueColor
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = SubtitleInk
        )
    }
}

@Composable
private fun HistoryScanCard(
    record: ScanRecord,
    onClick: () -> Unit
) {
    SeniorCard(
        backgroundColor = Color.White,
        borderColor = Color(0xFFE5E0F2),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val riskLevel = try {
                RiskLevel.valueOf(record.riskLevel)
            } catch (e: Exception) {
                RiskLevel.SUSPICIOUS
            }
            RiskBadge(riskLevel = riskLevel)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = DarkInk,
                    maxLines = 1
                )
                Text(
                    text = record.snippet,
                    style = MaterialTheme.typography.bodySmall,
                    color = SubtitleInk,
                    maxLines = 1
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Details",
                tint = SubtitleInk,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private val SubtitleInk = Color(0xFF57526D)
