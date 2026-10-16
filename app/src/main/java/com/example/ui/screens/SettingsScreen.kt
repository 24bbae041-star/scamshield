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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import com.example.auth.GoogleAuthHelper
import com.example.data.AppLanguage
import com.example.model.TrustedContact
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteEmojiSticker
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
import com.example.ui.theme.SeniorTextScale
import com.example.ui.theme.WarningRose
import com.example.ui.theme.WarningRoseContainer

@Composable
fun SettingsScreen(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val textScale by viewModel.textScale.collectAsState()
    val highContrast by viewModel.highContrast.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val speechSpeed by viewModel.speechSpeed.collectAsState()
    val trustedContacts by viewModel.trustedContacts.collectAsState()

    var showAddContactDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    // Add Contact Dialog
    if (showAddContactDialog) {
        var contactName by remember { mutableStateOf("") }
        var contactRelation by remember { mutableStateOf("Son") }
        var contactPhone by remember { mutableStateOf("") }
        var isPrimary by remember { mutableStateOf(trustedContacts.isEmpty()) }

        AlertDialog(
            onDismissRequest = { showAddContactDialog = false },
            title = {
                Text("Add Trusted Family Contact", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text("Name (e.g. Ramesh Sharma)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = contactRelation,
                        onValueChange = { contactRelation = it },
                        label = { Text("Relationship (e.g. Son, Daughter, Grandchild)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = contactPhone,
                        onValueChange = { contactPhone = it },
                        label = { Text("Phone Number (e.g. +91 98765 43210)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Set as primary SOS contact", style = MaterialTheme.typography.bodyMedium)
                        Switch(checked = isPrimary, onCheckedChange = { isPrimary = it })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (contactName.isNotBlank() && contactPhone.isNotBlank()) {
                            viewModel.addTrustedContact(contactName, contactRelation, contactPhone, isPrimary)
                            showAddContactDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPurple)
                ) {
                    Text("Save Contact")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddContactDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }

    // Delete All Data Dialog
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text("Erase All Local Data?", color = WarningRose) },
            text = {
                Text("This will permanently delete your scan history, trusted contacts, and quiz progress from this phone. This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllData()
                        showDeleteConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WarningRose)
                ) {
                    Text("Erase Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }

    // Privacy Dialog
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Scan Shield Privacy Commitment 💜") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("• Local Storage: Your trusted contacts and scan history are saved locally on your phone in an encrypted Room SQLite database.", style = MaterialTheme.typography.bodySmall)
                    Text("• Screenshot Processing: When you analyze a screenshot, image content is sent strictly to the Gemini API for safety classification. It is never logged or used for advertising.", style = MaterialTheme.typography.bodySmall)
                    Text("• No Financial Access: Scan Shield NEVER asks for your bank password, debit card PIN, CVV, or UPI PIN.", style = MaterialTheme.typography.bodySmall)
                    Text("• No Continuous Audio: Voice assistant only activates when you tap the microphone button.", style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                Button(onClick = { showPrivacyDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = DeepPurple)) {
                    Text("Understood")
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
                CuteStickerBadge(type = StickerType.SHIELD, size = 48.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Settings & Accessibility",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Language, text scaling, speech speed, and contacts",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF57526D)
                    )
                }
            }
        }

        // 1. Language Selection (9 Indian Languages)
        item {
            SeniorCard(backgroundColor = Color.White, borderColor = Lavender) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = DeepPurple)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Select Language (9 Indian Languages)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        AppLanguage.values().forEach { lang ->
                            val isSelected = currentLang == lang
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) LavenderContainer else Color(0xFFFAF8FE))
                                    .border(1.dp, if (isSelected) DeepPurple else Color(0xFFE2DCF0), RoundedCornerShape(12.dp))
                                    .clickable { viewModel.switchLanguage(lang) }
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                                    .testTag("lang_select_${lang.code}")
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${lang.displayName} (${lang.nativeName})",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isSelected) DeepPurple else DarkInk
                                    )
                                    if (isSelected) {
                                        Text(text = "✓ Active", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = DeepPurple)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Global Senior Text Size (Normal, Large, Extra Large)
        item {
            SeniorCard(backgroundColor = Color.White, borderColor = Lavender) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.FormatSize, contentDescription = null, tint = DeepPurple)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Text Size for Senior Readability",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Updates the entire application immediately for easy reading.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF57526D)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SeniorTextScale.values().forEach { scale ->
                            val isSelected = textScale == scale
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) DeepPurple else Color(0xFFFAF8FE))
                                    .border(1.5.dp, if (isSelected) DeepPurple else Color(0xFFE2DCF0), RoundedCornerShape(14.dp))
                                    .clickable { viewModel.setTextScale(scale) }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = scale.label,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isSelected) Color.White else DarkInk
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Speech Rate & Voice Settings
        item {
            SeniorCard(backgroundColor = Color.White, borderColor = Mint) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = MintDark)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Voice Assistant Speech Speed",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = DarkInk
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Speed: ${if (speechSpeed <= 0.85f) "Comfortable & Clear (Senior Mode)" else "Normal"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF57526D)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = speechSpeed,
                        onValueChange = { viewModel.setSpeechSpeed(it) },
                        valueRange = 0.6f..1.2f,
                        steps = 5,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SeniorOutlinedButton(
                            text = "Test Spoken Voice",
                            icon = Icons.Default.VolumeUp,
                            onClick = {
                                viewModel.speakText(tts, "Namaste! Scan Shield voice assistance is working smoothly.")
                            }
                        )
                    }
                }
            }
        }

        // 4. Visual Contrast & Dark Theme
        item {
            SeniorCard(backgroundColor = Color.White, borderColor = Color(0xFFE2DCF0)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Contrast, contentDescription = null, tint = DeepPurple)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("High-Contrast Mode", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = DarkInk)
                                Text("Sharp borders and pure black text", style = MaterialTheme.typography.bodySmall, color = Color(0xFF57526D))
                            }
                        }
                        Switch(checked = highContrast, onCheckedChange = { viewModel.toggleHighContrast(it) })
                    }
                    Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF1ECFC))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Brightness4, contentDescription = null, tint = DeepPurple)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Dark Theme", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = DarkInk)
                                Text("Gentle on eyes at night", style = MaterialTheme.typography.bodySmall, color = Color(0xFF57526D))
                            }
                        }
                        Switch(checked = isDarkTheme, onCheckedChange = { viewModel.toggleDarkTheme(it) })
                    }
                }
            }
        }

        // 5. Trusted Family Contacts Manager
        item {
            SeniorCard(backgroundColor = Color.White, borderColor = Lavender) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = DeepPurple)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Trusted Family Contacts",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkInk
                            )
                        }
                        IconButton(onClick = { showAddContactDialog = true }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add contact", tint = DeepPurple)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (trustedContacts.isEmpty()) {
                        Text(
                            text = "No contacts saved. Add a family member who can assist in an emergency.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF57526D)
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            trustedContacts.forEach { contact ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFF7F5FC))
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CuteEmojiSticker(emoji = contact.avatarEmoji, size = 36.dp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(contact.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = DarkInk)
                                                if (contact.isPrimary) {
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text("★ Primary", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = DeepPurple)
                                                }
                                            }
                                            Text("${contact.relationship} • ${contact.phoneNumber}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF57526D))
                                        }
                                    }

                                    IconButton(onClick = { viewModel.deleteTrustedContact(contact) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. Privacy & Data Deletion
        item {
            SeniorCard(backgroundColor = Color.White, borderColor = Color(0xFFE2DCF0)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showPrivacyDialog = true }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.PrivacyTip, contentDescription = null, tint = DeepPurple)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Privacy Policy & AI Transparency", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = DarkInk)
                        }
                        Text("View", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = DeepPurple)
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF1ECFC))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDeleteConfirmDialog = true }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = WarningRose)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Erase Scan History & App Data", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = WarningRose)
                        }
                        Text("Reset", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = WarningRose)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
