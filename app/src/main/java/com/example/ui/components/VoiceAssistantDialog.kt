package com.example.ui.components

import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.ui.AppScreen
import com.example.ui.ScanShieldViewModel
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.Mint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintDark
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.WarningRose

@Composable
fun VoiceAssistantDialog(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    val initialMessage by viewModel.voiceDialogMessage.collectAsState()

    var activeAnswerText by remember { mutableStateOf(initialMessage) }
    var activeTitle by remember { mutableStateOf("Scan Shield Voice Assistant") }

    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val micScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mic_scale"
    )

    AlertDialog(
        onDismissRequest = {
            viewModel.stopSpeaking(tts)
            onDismiss()
        },
        title = null,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CuteShieldMascot(size = 38.dp, showSparkles = false)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = activeTitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.stopSpeaking(tts)
                            onDismiss()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Big Animated Microphone Button
                Box(
                    modifier = Modifier
                        .scale(if (isSpeaking) micScale else 1f)
                        .size(80.dp)
                        .shadow(6.dp, CircleShape)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                if (isSpeaking) listOf(Mint, MintDark) else listOf(Lavender, DeepPurple)
                            )
                        )
                        .border(3.dp, Color.White, CircleShape)
                        .clickable {
                            if (isSpeaking) {
                                viewModel.stopSpeaking(tts)
                            } else {
                                viewModel.speakText(tts, activeAnswerText)
                            }
                        }
                        .testTag("voice_assistant_mic_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.Mic,
                        contentDescription = "Microphone",
                        tint = Color.White,
                        modifier = Modifier.size(38.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isSpeaking) "Speaking aloud now... Tap to stop" else "Tap microphone to hear response",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isSpeaking) MintDark else DeepPurple
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Spoken Answer Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFAF8FE))
                        .border(1.5.dp, Lavender, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text(
                            text = activeAnswerText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarkInk
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Audio Playback Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { viewModel.speakText(tts, activeAnswerText) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Repeat", tint = DeepPurple)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = { viewModel.stopSpeaking(tts) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop", tint = WarningRose)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Tap any question below to ask Scan Shield:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF57526D)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Recommended Voice Commands
                val commands = listOf(
                    "Is this message fake?" to "If a message threatens that your pension or electricity will be blocked within hours, asks for an OTP, or gives a bit.ly link, it is almost certainly a scam. Go to the Scanner tab to check the exact text or screenshot.",
                    "Scan this screenshot." to "Opening the AI Screenshot Scanner now. You can pick any screenshot or photo from your gallery to inspect red flags.",
                    "Help me verify a payment." to "Opening Pause Before You Pay. Always remember: You never need to enter your UPI PIN to receive money! Entering a PIN authorises money to leave your account.",
                    "Call my trusted contact." to "Opening your trusted family emergency dialler so you can speak directly with your family.",
                    "How can I report cyber fraud?" to "Dial helpline 1930 immediately! It is India's National Cyber Financial Fraud Helpline. If you report within the first 2 hours, police can freeze the stolen money."
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    commands.forEach { (cmd, response) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(LavenderContainer)
                                .clickable {
                                    activeTitle = cmd
                                    activeAnswerText = response
                                    viewModel.speakText(tts, response)
                                    if (cmd == "Scan this screenshot.") {
                                        viewModel.closeVoiceAssistant()
                                        viewModel.navigateTo(AppScreen.SCANNER)
                                    } else if (cmd == "Help me verify a payment.") {
                                        viewModel.closeVoiceAssistant()
                                        viewModel.navigateTo(AppScreen.PAUSE_PAY)
                                    } else if (cmd == "Call my trusted contact.") {
                                        viewModel.closeVoiceAssistant()
                                        viewModel.navigateTo(AppScreen.EMERGENCY_SOS)
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("💬", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = cmd,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = DeepPurple
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}
