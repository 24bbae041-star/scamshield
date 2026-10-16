package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.ScamDatabase
import com.example.model.BankEmergencyContact
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteEmojiSticker
import com.example.ui.components.CuteShieldMascot
import com.example.ui.components.CuteStickerBadge
import com.example.ui.components.SeniorCard
import com.example.ui.components.SeniorOutlinedButton
import com.example.ui.components.SeniorPrimaryButton
import com.example.ui.components.StickerType
import com.example.ui.theme.ButterYellowContainer
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.Mint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintDark
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.WarningRose
import com.example.ui.theme.WarningRoseContainer
import com.example.ui.theme.WarningRoseDark

@Composable
fun EmergencySosScreen(
    viewModel: ScanShieldViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val context = LocalContext.current
    val trustedContacts by viewModel.trustedContacts.collectAsState()

    var showCallConfirmDialog by remember { mutableStateOf<Pair<String, String>?>(null) } // (Name, PhoneNumber)

    if (showCallConfirmDialog != null) {
        val (contactName, contactPhone) = showCallConfirmDialog!!
        AlertDialog(
            onDismissRequest = { showCallConfirmDialog = null },
            title = {
                Text(
                    text = "Confirm Emergency Call",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(
                    text = "This will open your phone's dialler to call $contactName at $contactPhone. You will have full control to proceed with the call.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${contactPhone.replace(" ", "")}")
                        }
                        context.startActivity(dialIntent)
                        showCallConfirmDialog = null
                    }
                ) {
                    Text("Open Dialler", color = DeepPurple, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCallConfirmDialog = null }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }

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
                        text = "Scam Emergency Centre",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Instant response, cybercrime helplines & bank freeze",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF57526D)
                    )
                }
            }
        }

        // 1. National Cybercrime Helpline 1930 (Golden Hour)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(WarningRoseContainer)
                    .border(2.5.dp, WarningRose, RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CuteShieldMascot(size = 56.dp, showSparkles = true)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "CYBERCRIME HELPLINE",
                                style = MaterialTheme.typography.labelLarge.copy(color = WarningRoseDark)
                            )
                            Text(
                                text = "Dial 1930 Immediately",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                color = WarningRoseDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "If money was deducted or you shared an OTP, dial 1930 within the 'Golden Hour' (first 2 hours). Police can freeze the fraudulent transaction across banking channels.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkInk
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    SeniorPrimaryButton(
                        text = "Call 1930 Now (National Helpline)",
                        icon = Icons.Default.Call,
                        containerColor = WarningRose,
                        contentColor = Color.White,
                        testTag = "call_1930_emergency_button",
                        onClick = {
                            showCallConfirmDialog = Pair("National Cyber Financial Fraud Helpline", "1930")
                        }
                    )
                }
            }
        }

        // 2. Official National Cybercrime Portal Link
        item {
            SeniorCard(
                backgroundColor = Color.White,
                borderColor = Lavender
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CuteStickerBadge(type = StickerType.SHIELD, size = 46.dp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Official Cybercrime Portal",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                        Text(
                            text = "https://cybercrime.gov.in/ (Govt of India)",
                            style = MaterialTheme.typography.bodySmall,
                            color = DeepPurple
                        )
                    }
                    IconButton(
                        onClick = {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cybercrime.gov.in/"))
                            context.startActivity(browserIntent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = "Open portal",
                            tint = DeepPurple
                        )
                    }
                }
            }
        }

        // 3. Call Trusted Family Members
        item {
            Text(
                text = "Your Trusted Family Contacts",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (trustedContacts.isEmpty()) {
            item {
                SeniorCard(backgroundColor = Color.White, borderColor = Color(0xFFE2DCF0)) {
                    Text(
                        text = "No trusted family contacts added yet. Go to Settings to add your children or grandchildren.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            items(trustedContacts) { contact ->
                SeniorCard(
                    backgroundColor = Color.White,
                    borderColor = if (contact.isPrimary) Lavender else Color(0xFFE5E0F2)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CuteEmojiSticker(emoji = contact.avatarEmoji, size = 42.dp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = contact.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = DarkInk
                                )
                                Text(
                                    text = "${contact.relationship} • ${contact.phoneNumber}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF57526D)
                                )
                            }
                        }

                        IconButton(
                            onClick = { showCallConfirmDialog = Pair(contact.name, contact.phoneNumber) },
                            modifier = Modifier
                                .background(MintContainer, RoundedCornerShape(12.dp))
                                .testTag("sos_call_${contact.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call ${contact.name}",
                                tint = MintDark
                            )
                        }
                    }
                }
            }
        }

        // 4. Bank Emergency Freeze Contacts
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Bank 24x7 Emergency Freeze Helplines",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Direct numbers to block debit cards and UPI in case of fraud",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF57526D)
            )
        }

        items(ScamDatabase.BANK_EMERGENCY_CONTACTS) { bank ->
            SeniorCard(
                backgroundColor = Color.White,
                borderColor = Color(0xFFE2DCF0)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = bank.bankName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(LavenderContainer)
                                .clickable {
                                    showCallConfirmDialog = Pair(bank.bankName, bank.tollFreeNumber)
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = DeepPurple,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = bank.tollFreeNumber,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = DeepPurple
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Card Block: ${bank.cardBlockNumber} • ${bank.upiBlockSms}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF57526D)
                    )
                }
            }
        }

        // 5. Golden Hour Emergency Checklist
        item {
            SeniorCard(
                backgroundColor = ButterYellowContainer,
                borderColor = Color(0xFFFFD56B)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Golden Hour Checklist (First 2 Hours)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("1. Call 1930 or your bank to block your debit card and freeze UPI.", style = MaterialTheme.typography.bodyMedium, color = DarkInk)
                        Text("2. Take screenshots of SMS alerts, transaction IDs, and WhatsApp chats as evidence.", style = MaterialTheme.typography.bodyMedium, color = DarkInk)
                        Text("3. If you installed AnyDesk or TeamViewer, immediately uninstall it and disconnect Wi-Fi.", style = MaterialTheme.typography.bodyMedium, color = DarkInk)
                        Text("4. Tell a family member right away. Do not feel embarrassed — scammers use sophisticated psychological tricks.", style = MaterialTheme.typography.bodyMedium, color = DarkInk)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
