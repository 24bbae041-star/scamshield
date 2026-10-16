package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RiskLevel
import com.example.model.ScanRecord
import com.example.ui.AppScreen
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteShieldMascot
import com.example.ui.components.CuteStickerBadge
import com.example.ui.components.SeniorCard
import com.example.ui.components.SeniorOutlinedButton
import com.example.ui.components.SeniorPrimaryButton
import com.example.ui.components.StickerType
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
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenContainer
import com.example.ui.theme.WarningRose
import com.example.ui.theme.WarningRoseContainer
import com.example.ui.theme.WarningRoseDark

@Composable
fun ScanResultScreen(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val context = LocalContext.current
    val record by viewModel.activeScanRecord.collectAsState()

    if (record == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No scan result to display.")
            Spacer(modifier = Modifier.height(12.dp))
            SeniorPrimaryButton(text = "Go to Home", onClick = { viewModel.navigateTo(AppScreen.HOME) })
        }
        return
    }

    val item = record!!
    val risk = try {
        RiskLevel.valueOf(item.riskLevel)
    } catch (e: Exception) {
        RiskLevel.SUSPICIOUS
    }

    val (bannerBg, bannerBorder, bannerTitle, bannerDesc) = when (risk) {
        RiskLevel.HIGH_RISK -> Quad(
            WarningRoseContainer,
            WarningRose,
            "🚨 HIGH RISK DETECTED",
            "This message shows clear red flags of a financial scam or impersonation attempt. Do NOT click links or pay money."
        )
        RiskLevel.SUSPICIOUS -> Quad(
            CautionOrangeContainer,
            CautionOrange,
            "⚠️ SUSPICIOUS ACTIVITY",
            "This contains unverified claims or irregular links. Proceed with extreme caution and verify before responding."
        )
        RiskLevel.SAFE_VERIFIED -> Quad(
            SafeGreenContainer,
            SafeGreen,
            "✅ NO OBVIOUS WARNING SIGNS",
            "No common scam triggers were found. Notice: Always remain vigilant; never share your UPI PIN or banking passwords."
        )
        RiskLevel.UNABLE_TO_VERIFY -> Quad(
            LavenderContainer,
            DeepPurple,
            "❓ UNABLE TO VERIFY",
            "Not enough data to confirm authenticity. Contact your local bank branch directly to verify."
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DeepPurple
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Safety Verification",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            val speechContent = "${item.title}. $bannerTitle. $bannerDesc. ${item.explanation}. Recommended next step: ${item.nextSteps}"
                            viewModel.speakText(tts, speechContent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak aloud",
                            tint = DeepPurple
                        )
                    }
                    IconButton(
                        onClick = { viewModel.deleteScanRecord(item) },
                        modifier = Modifier.testTag("delete_scan_record_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete record",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }

        // 1. Verdict Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(bannerBg)
                    .border(2.dp, bannerBorder, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CuteShieldMascot(size = 54.dp, showSparkles = false)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = bannerTitle,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                color = DarkInk
                            )
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = DarkInk
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = bannerDesc,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = DarkInk
                    )
                }
            }
        }

        // 2. Original Scanned Snippet
        item {
            SeniorCard(
                backgroundColor = Color.White,
                borderColor = Color(0xFFE2DCF0)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Scanned Content",
                        style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF57526D))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.snippet,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkInk
                    )
                }
            }
        }

        // 3. Evidence Breakdown / Red Flags
        val evidenceItems = item.getEvidenceItems()
        if (evidenceItems.isNotEmpty()) {
            item {
                SeniorCard(
                    backgroundColor = Color.White,
                    borderColor = if (risk == RiskLevel.HIGH_RISK) WarningRose.copy(alpha = 0.5f) else Lavender
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CuteStickerBadge(type = StickerType.WARNING, size = 36.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Evidence & Warning Signs Detected",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkInk
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            evidenceItems.forEach { flag ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "•",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            color = if (risk == RiskLevel.HIGH_RISK) WarningRose else DeepPurple
                                        ),
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = flag,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                        color = DarkInk
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Simple Explanation
        item {
            SeniorCard(
                backgroundColor = LavenderContainer,
                borderColor = Lavender
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "What Does This Mean?",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.explanation,
                        style = MaterialTheme.typography.bodyLarge,
                        color = DarkInk
                    )
                }
            }
        }

        // 5. Recommended Next Steps
        item {
            SeniorCard(
                backgroundColor = MintContainer,
                borderColor = Mint
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Recommended Next Steps",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MintDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.nextSteps,
                        style = MaterialTheme.typography.bodyLarge,
                        color = DarkInk
                    )
                }
            }
        }

        // 6. Safe Way to Verify Independently
        item {
            SeniorCard(
                backgroundColor = ButterYellowContainer,
                borderColor = Color(0xFFFFD56B)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CuteStickerBadge(type = StickerType.MAGNIFYING_GLASS, size = 36.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "How to Verify Safely",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.independentVerification,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkInk
                    )
                }
            }
        }

        // 7. Actions: Emergency SOS or Done
        item {
            if (risk == RiskLevel.HIGH_RISK) {
                SeniorPrimaryButton(
                    text = "Report to Helpline 1930 / Bank SOS",
                    icon = Icons.Default.Call,
                    containerColor = WarningRose,
                    testTag = "scan_result_sos_button",
                    onClick = { viewModel.navigateTo(AppScreen.EMERGENCY_SOS) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            SeniorOutlinedButton(
                text = "Back to Home Dashboard",
                testTag = "scan_result_done_button",
                onClick = { viewModel.navigateTo(AppScreen.HOME) }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
