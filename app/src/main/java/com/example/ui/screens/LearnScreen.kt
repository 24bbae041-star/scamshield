package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ScamDatabase
import com.example.model.QuizScenario
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteShieldMascot
import com.example.ui.components.CuteStickerBadge
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
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenContainer
import com.example.ui.theme.WarningRose
import com.example.ui.theme.WarningRoseContainer
import com.example.ui.theme.WarningRoseDark

@Composable
fun LearnScreen(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?,
    modifier: Modifier = Modifier
) {
    val completedQuizzes by viewModel.completedQuizzes.collectAsState()
    val earnedBadges by viewModel.earnedBadges.collectAsState()

    var activeTab by remember { mutableStateOf("quizzes") } // "quizzes" or "guide"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CuteStickerBadge(type = StickerType.HEART, size = 48.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Safety Learning Centre",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Interactive scenario quizzes and offline senior handbook",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF57526D)
                    )
                }
            }
        }

        // Progress & Badges Showcase
        item {
            SeniorCard(
                backgroundColor = LavenderContainer,
                borderColor = Lavender
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = DeepPurple,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Your Safety Badges",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkInk
                            )
                        }
                        Text(
                            text = "${completedQuizzes.size} of 12 Quizzes Completed",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = DeepPurple
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (earnedBadges.isEmpty()) {
                        Text(
                            text = "Solve scenario quizzes below to unlock your first digital safety badge! 🛡️",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF57526D)
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(earnedBadges.toList()) { badge ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.White)
                                        .border(1.5.dp, DeepPurple, RoundedCornerShape(12.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = badge,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = DeepPurple
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Toggle Switch: 12 Scenario Quizzes vs Offline Safety Guide
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFEDE8F8))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (activeTab == "quizzes") DeepPurple else Color.Transparent)
                        .clickable { activeTab = "quizzes" }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "12 Scenario Quizzes",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (activeTab == "quizzes") Color.White else DarkInk
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (activeTab == "guide") DeepPurple else Color.Transparent)
                        .clickable { activeTab = "guide" }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Offline Safety Guide",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (activeTab == "guide") Color.White else DarkInk
                    )
                }
            }
        }

        if (activeTab == "quizzes") {
            items(ScamDatabase.QUIZ_SCENARIOS) { scenario ->
                val userAnswer = completedQuizzes[scenario.id]
                QuizScenarioCard(
                    scenario = scenario,
                    userAnswerIndex = userAnswer,
                    tts = tts,
                    onSelectOption = { index ->
                        viewModel.answerQuiz(scenario, index)
                    },
                    onSpeak = { text ->
                        viewModel.speakText(tts, text)
                    }
                )
            }
        } else {
            // Offline Senior Safety Handbook
            item {
                SeniorHandbookSection(tts = tts, onSpeak = { viewModel.speakText(tts, it) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun QuizScenarioCard(
    scenario: QuizScenario,
    userAnswerIndex: Int?,
    tts: TextToSpeech?,
    onSelectOption: (Int) -> Unit,
    onSpeak: (String) -> Unit
) {
    val isAnswered = userAnswerIndex != null
    val isCorrect = userAnswerIndex == scenario.correctIndex

    SeniorCard(
        backgroundColor = Color.White,
        borderColor = if (isAnswered) {
            if (isCorrect) SafeGreen else WarningRose
        } else Lavender
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LavenderContainer)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = scenario.category,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = DeepPurple
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            val questionText = "${scenario.title}. Scenario: ${scenario.scenarioText}"
                            onSpeak(questionText)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = DeepPurple)
                    }
                    if (isAnswered) {
                        Text(
                            text = if (isCorrect) "✅ Correct" else "❌ Review",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isCorrect) SafeGreen else WarningRose
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = scenario.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = DarkInk
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = scenario.scenarioText,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkInk
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                scenario.options.forEachIndexed { index, optionText ->
                    val isSelected = userAnswerIndex == index
                    val isOptionCorrect = index == scenario.correctIndex

                    val (bg, border, textColor) = if (isAnswered) {
                        if (isOptionCorrect) {
                            Triple(SafeGreenContainer, SafeGreen, Color(0xFF0C522C))
                        } else if (isSelected) {
                            Triple(WarningRoseContainer, WarningRose, WarningRoseDark)
                        } else {
                            Triple(Color(0xFFF9F8FD), Color(0xFFE5E0F2), Color.Gray)
                        }
                    } else {
                        Triple(Color(0xFFF9F8FD), Color(0xFFE2DCF0), DarkInk)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(bg)
                            .border(1.5.dp, border, RoundedCornerShape(14.dp))
                            .clickable(enabled = !isAnswered) {
                                onSelectOption(index)
                            }
                            .padding(12.dp)
                            .testTag("quiz_${scenario.id}_option_$index")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${('A' + index)}. ",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = textColor
                            )
                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                color = textColor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            if (isAnswered) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isCorrect) MintContainer else ButterYellowContainer)
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = if (isCorrect) "🎉 Badge Earned: ${scenario.badgeEarned}" else "💡 Safety Explanation",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = if (isCorrect) MintDark else Color(0xFF7A5400)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = scenario.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarkInk
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SeniorHandbookSection(
    tts: TextToSpeech?,
    onSpeak: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SeniorCard(
            backgroundColor = Color.White,
            borderColor = Lavender
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CuteStickerBadge(type = StickerType.SHIELD, size = 42.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "1. Digital Life Certificate (DLC) Guide",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Jeevan Pramaan submission is available every year in October & November.\n" +
                            "• Official apps are available ONLY on Google Play: 'Jeevan Pramaan' and 'AadhaarFaceRD'.\n" +
                            "• You can also request India Post Payment Bank (IPPB) postman to visit your doorstep for DLC generation for a nominal fee of Rs 50.\n" +
                            "• NEVER install APK files sent on WhatsApp or SMS.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkInk
                )
            }
        }

        SeniorCard(
            backgroundColor = Color.White,
            borderColor = ButterYellow
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CuteStickerBadge(type = StickerType.COIN, size = 42.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "2. Safe UPI & Mobile Banking Handbook",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• UPI PIN is like your ATM PIN: Only use it when you WANT TO SEND MONEY.\n" +
                            "• Receiving money requires ZERO passwords or PINs.\n" +
                            "• Never scan a QR code sent by an unknown buyer on OLX/Quikr.\n" +
                            "• Set a daily transaction limit of Rs 10,000 on your UPI app so even if your phone is lost, your savings are safe.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkInk
                )
            }
        }

        SeniorCard(
            backgroundColor = Color.White,
            borderColor = Mint
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CuteStickerBadge(type = StickerType.PHONE, size = 42.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "3. Fake Customer Care Numbers",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Never search Google for phone numbers of banks, airlines, couriers, or gas agencies.\n" +
                            "• Scammers buy top Google ads with their private mobile numbers.\n" +
                            "• Always take phone numbers directly from your physical bank passbook or your utility bill.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkInk
                )
            }
        }
    }
}
