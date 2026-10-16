package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Localization
import com.example.ui.AppScreen
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteShieldMascot
import com.example.ui.components.CuteStickerBadge
import com.example.ui.components.SeniorCard
import com.example.ui.components.SeniorOutlinedButton
import com.example.ui.components.SeniorPrimaryButton
import com.example.ui.components.StickerType
import com.example.ui.theme.ButterYellow
import com.example.ui.theme.ButterYellowContainer
import com.example.ui.theme.CautionOrange
import com.example.ui.theme.CautionOrangeContainer
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.Mint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintDark
import com.example.ui.theme.Peach
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenContainer
import com.example.ui.theme.WarningRose
import com.example.ui.theme.WarningRoseContainer
import com.example.ui.theme.WarningRoseDark

@Composable
fun PauseBeforePayScreen(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val context = LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()

    var q1KnowPersonally by remember { mutableStateOf<Boolean?>(null) }
    var q2UrgencyOrThreat by remember { mutableStateOf<Boolean?>(null) }
    var q3OtpPinOrScreenShare by remember { mutableStateOf<Boolean?>(null) }
    var q4ScannedQrToReceive by remember { mutableStateOf<Boolean?>(null) }
    var q5VerifiedKnownNumber by remember { mutableStateOf<Boolean?>(null) }

    val allAnswered = q1KnowPersonally != null &&
            q2UrgencyOrThreat != null &&
            q3OtpPinOrScreenShare != null &&
            q4ScannedQrToReceive != null &&
            q5VerifiedKnownNumber != null

    // Determine verdict
    val isHighRisk = (q3OtpPinOrScreenShare == true) || (q4ScannedQrToReceive == true) || (q2UrgencyOrThreat == true && q1KnowPersonally == false)
    val isCaution = !isHighRisk && (q1KnowPersonally == false || q5VerifiedKnownNumber == false)
    val isSafe = allAnswered && !isHighRisk && !isCaution

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = DeepPurple
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "Pause Before You Pay",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "A 2-minute safety check before transferring money",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF57526D)
                    )
                }
            }
        }

        // Hero Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(PeachContainer)
                    .border(2.dp, Peach, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CuteShieldMascot(size = 64.dp, showSparkles = true)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = Localization.get("pause_heading", currentLang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Take a breath. Answer these 5 simple questions to protect your pension savings.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarkInk
                        )
                    }
                }
            }
        }

        // Important UPI Rule Callout
        item {
            SeniorCard(
                backgroundColor = ButterYellowContainer,
                borderColor = ButterYellow
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CuteStickerBadge(type = StickerType.COIN, size = 42.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Golden Rule of UPI",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = Localization.get("upi_pin_rule", currentLang),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = Color(0xFF634100)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.speakText(tts, Localization.get("upi_pin_rule", currentLang)) }
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = DarkInk)
                    }
                }
            }
        }

        // 5 Guided Questions
        item {
            Text(
                text = "Safety Checklist",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Question 1
        item {
            QuestionCard(
                number = "1",
                question = "Do you know the recipient personally in real life?",
                subtext = "e.g., your child, grandchild, local grocery shopkeeper, known family friend",
                selectedValue = q1KnowPersonally,
                onAnswer = { q1KnowPersonally = it }
            )
        }

        // Question 2
        item {
            QuestionCard(
                number = "2",
                question = "Are they creating urgency or threatening you with police or disconnection?",
                subtext = "e.g., 'Pay within 30 minutes', electricity cut off tonight, CBI arrest threat, courier seized",
                selectedValue = q2UrgencyOrThreat,
                onAnswer = { q2UrgencyOrThreat = it }
            )
        }

        // Question 3
        item {
            QuestionCard(
                number = "3",
                question = "Did they ask you to share an OTP, enter a UPI PIN to receive money, or install AnyDesk?",
                subtext = "Any request for OTP, remote app, or screen-sharing is an immediate fraud warning sign",
                selectedValue = q3OtpPinOrScreenShare,
                onAnswer = { q3OtpPinOrScreenShare = it }
            )
        }

        // Question 4
        item {
            QuestionCard(
                number = "4",
                question = "Did they send you a QR code and claim you will 'receive' money by scanning it?",
                subtext = "Scanning a QR code ALWAYS sends money out of your bank account",
                selectedValue = q4ScannedQrToReceive,
                onAnswer = { q4ScannedQrToReceive = it }
            )
        }

        // Question 5
        item {
            QuestionCard(
                number = "5",
                question = "Can you call this person or organization on their known, verified phone number first?",
                subtext = "Do not use phone numbers given inside suspicious WhatsApp forwards or SMS",
                selectedValue = q5VerifiedKnownNumber,
                onAnswer = { q5VerifiedKnownNumber = it }
            )
        }

        // Final Verdict Card
        if (allAnswered) {
            item {
                AnimatedVisibility(visible = true) {
                    if (isHighRisk) {
                        VerdictResultCard(
                            title = "🛑 STOP AND VERIFY — HIGH RISK!",
                            message = "DO NOT PROCEED WITH THIS PAYMENT. Several major scam warning signs were triggered. If you send money, it cannot be reversed. Call your trusted family member or 1930 immediately.",
                            backgroundColor = WarningRoseContainer,
                            borderColor = WarningRose,
                            textColor = WarningRoseDark,
                            primaryActionText = "Call Cyber Helpline 1930",
                            onPrimaryAction = {
                                val intent = Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:1930") }
                                context.startActivity(intent)
                            }
                        )
                    } else if (isCaution) {
                        VerdictResultCard(
                            title = "⚠️ PROCEED WITH CAUTION",
                            message = "You are sending money to someone you do not know personally or cannot verify. Call a family member or bank branch before pressing 'Pay'.",
                            backgroundColor = CautionOrangeContainer,
                            borderColor = CautionOrange,
                            textColor = Color(0xFF7A3600),
                            primaryActionText = "Call Trusted Family Contact",
                            onPrimaryAction = { viewModel.navigateTo(AppScreen.HOME) }
                        )
                    } else {
                        VerdictResultCard(
                            title = "✅ SAFE TO PROCEED",
                            message = "No scam red flags detected. You know the recipient, no panic or threats were made, and no OTP/screen sharing was requested.",
                            backgroundColor = SafeGreenContainer,
                            borderColor = SafeGreen,
                            textColor = Color(0xFF0F5A32),
                            primaryActionText = "Done",
                            onPrimaryAction = { viewModel.navigateTo(AppScreen.HOME) }
                        )
                    }
                }
            }
        }

        item {
            SeniorOutlinedButton(
                text = "Reset Checklist",
                icon = Icons.Default.Refresh,
                testTag = "reset_pause_checklist",
                onClick = {
                    q1KnowPersonally = null
                    q2UrgencyOrThreat = null
                    q3OtpPinOrScreenShare = null
                    q4ScannedQrToReceive = null
                    q5VerifiedKnownNumber = null
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun QuestionCard(
    number: String,
    question: String,
    subtext: String,
    selectedValue: Boolean?,
    onAnswer: (Boolean) -> Unit
) {
    SeniorCard(
        backgroundColor = Color.White,
        borderColor = if (selectedValue != null) Lavender else Color(0xFFE5E0F2)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(LavenderContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = DeepPurple
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = question,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtext,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF57526D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ChoiceButton(
                    text = "Yes",
                    isSelected = selectedValue == true,
                    onClick = { onAnswer(true) },
                    modifier = Modifier.weight(1f)
                )
                ChoiceButton(
                    text = "No",
                    isSelected = selectedValue == false,
                    onClick = { onAnswer(false) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ChoiceButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) DeepPurple else Color(0xFFF3EFFF))
            .border(1.5.dp, if (isSelected) DeepPurple else Lavender, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = if (isSelected) Color.White else DarkInk,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun VerdictResultCard(
    title: String,
    message: String,
    backgroundColor: Color,
    borderColor: Color,
    textColor: Color,
    primaryActionText: String,
    onPrimaryAction: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(backgroundColor)
            .border(2.5.dp, borderColor, RoundedCornerShape(22.dp))
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = DarkInk
            )
            Spacer(modifier = Modifier.height(14.dp))
            SeniorPrimaryButton(
                text = primaryActionText,
                onClick = onPrimaryAction,
                containerColor = borderColor,
                contentColor = Color.White
            )
        }
    }
}
