package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppLanguage
import com.example.data.Localization
import com.example.data.ScamDatabase
import com.example.model.RiskLevel
import com.example.model.ScanType
import com.example.model.ScamPreset
import com.example.ui.AppScreen
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteShieldMascot
import com.example.ui.components.CuteStickerBadge
import com.example.ui.components.SeniorCard
import com.example.ui.components.SeniorPrimaryButton
import com.example.ui.components.StickerType
import com.example.ui.theme.ButterYellow
import com.example.ui.theme.ButterYellowContainer
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.Mint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.WarningRose

@Composable
fun ScannerScreen(
    viewModel: ScanShieldViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val context = LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()

    var inputMessage by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var activeCategoryFilter by remember { mutableStateOf("All") }

    // Modern Android Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedImageUri = uri
        if (uri != null) {
            try {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                selectedBitmap = bitmap
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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
                        text = "AI Scam Scanner",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Check screenshots, SMS, calls, or test demo samples",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF57526D)
                    )
                }
            }
        }

        // Section 1: Upload Screenshot
        item {
            SeniorCard(
                backgroundColor = Color.White,
                borderColor = Lavender
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CuteStickerBadge(type = StickerType.SHIELD, size = 40.dp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "1. Scan a Screenshot",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkInk
                            )
                            Text(
                                text = "Select an image of a message, WhatsApp notice, or QR code",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF57526D)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (selectedBitmap != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.5.dp, DeepPurple, RoundedCornerShape(16.dp))
                        ) {
                            Image(
                                bitmap = selectedBitmap!!.asImageBitmap(),
                                contentDescription = "Selected screenshot preview",
                                modifier = Modifier.fillMaxSize()
                            )
                            IconButton(
                                onClick = {
                                    selectedImageUri = null
                                    selectedBitmap = null
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(50))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Remove",
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    SeniorPrimaryButton(
                        text = if (selectedBitmap == null) "Choose Screenshot from Photos" else "Change Screenshot",
                        icon = Icons.Default.Image,
                        containerColor = Lavender,
                        contentColor = DarkInk,
                        testTag = "select_screenshot_button",
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }
            }
        }

        // Section 2: Paste or Type Text
        item {
            SeniorCard(
                backgroundColor = Color.White,
                borderColor = ButterYellow
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CuteStickerBadge(type = StickerType.MAGNIFYING_GLASS, size = 40.dp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "2. Or Enter Suspicious Message / Link",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkInk
                            )
                            Text(
                                text = "Type or paste message text, phone number, or URL",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF57526D)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = inputMessage,
                        onValueChange = { inputMessage = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .testTag("scanner_input_textfield"),
                        placeholder = {
                            Text(
                                "Example: 'Dear pensioner your pension will stop submit DLC via bit.ly link...' or phone number",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        },
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    SeniorPrimaryButton(
                        text = if (isScanning) "Scanning with AI..." else "Scan & Verify Now 🛡️",
                        icon = Icons.Default.Search,
                        containerColor = DeepPurple,
                        testTag = "run_ai_scan_button",
                        onClick = {
                            if (inputMessage.isNotBlank() || selectedBitmap != null) {
                                val type = if (selectedBitmap != null) ScanType.SCREENSHOT
                                else if (inputMessage.startsWith("+") || inputMessage.all { it.isDigit() || it == '-' || it == ' ' }) ScanType.PHONE_NUMBER
                                else ScanType.MESSAGE_TEXT

                                viewModel.analyzeContent(inputMessage, selectedBitmap, type)
                            }
                        }
                    )

                    if (isScanning) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = DeepPurple,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Checking warning signs & official databases...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DeepPurple
                            )
                        }
                    }
                }
            }
        }

        // Section 3: Realistic Preset Demos (12 Scams + 4 Benign)
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Test with 16 Realistic Indian Presets",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "12 Unique Scam Cases + 4 Verified Benign Alerts",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF57526D)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val categories = listOf("All", "Pension", "KYC", "UPI", "Electricity", "Police", "Benign (Safe)")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = activeCategoryFilter == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) DeepPurple else LavenderContainer)
                                .clickable { activeCategoryFilter = cat }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else DarkInk,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        val allPresets = ScamDatabase.SCAM_PRESETS + ScamDatabase.BENIGN_PRESETS
        val filteredPresets = allPresets.filter {
            when (activeCategoryFilter) {
                "All" -> true
                "Benign (Safe)" -> !it.isScam
                else -> it.category.equals(activeCategoryFilter, ignoreCase = true)
            }
        }

        items(filteredPresets) { preset ->
            PresetCard(
                preset = preset,
                onClick = { viewModel.loadPresetScan(preset) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PresetCard(
    preset: ScamPreset,
    onClick: () -> Unit
) {
    SeniorCard(
        backgroundColor = if (preset.isScam) Color(0xFFFFF9F9) else Color(0xFFF6FFF9),
        borderColor = if (preset.isScam) WarningRose.copy(alpha = 0.4f) else SafeGreen.copy(alpha = 0.4f),
        onClick = onClick,
        modifier = Modifier.testTag("preset_card_${preset.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (preset.isScam) Icons.Default.Warning else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (preset.isScam) WarningRose else SafeGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = preset.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (preset.isScam) Color(0xFFFFE5E7) else Color(0xFFD7F5E3))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (preset.isScam) "🚨 Scam Test" else "✅ Safe Test",
                        color = if (preset.isScam) WarningRose else SafeGreen,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = preset.content,
                style = MaterialTheme.typography.bodySmall,
                color = DarkInk,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sender: ${preset.sender}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF736D88)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tap to Scan",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = DeepPurple
                    )
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = DeepPurple,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
