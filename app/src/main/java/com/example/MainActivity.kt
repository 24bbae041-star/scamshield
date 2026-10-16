package com.example

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.Localization
import com.example.ui.AppScreen
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.VoiceAssistantDialog
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.EmergencySosScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.PauseBeforePayScreen
import com.example.ui.screens.ScanResultScreen
import com.example.ui.screens.ScannerScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.ScanShieldTheme
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private val viewModel: ScanShieldViewModel by viewModels()
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        textToSpeech = TextToSpeech(this, this)

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            val highContrast by viewModel.highContrast.collectAsState()
            val textScale by viewModel.textScale.collectAsState()

            ScanShieldTheme(
                darkTheme = isDarkTheme,
                highContrast = highContrast,
                textScale = textScale
            ) {
                ScanShieldApp(
                    viewModel = viewModel,
                    tts = textToSpeech
                )
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.language = Locale("en", "IN")
        }
    }

    override fun onDestroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        super.onDestroy()
    }
}

@Composable
fun ScanShieldApp(
    viewModel: ScanShieldViewModel,
    tts: TextToSpeech?
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val showVoiceDialog by viewModel.showVoiceDialog.collectAsState()

    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = DeepPurple,
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = currentScreen == AppScreen.HOME,
                    onClick = { viewModel.navigateTo(AppScreen.HOME) },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(24.dp)) },
                    label = { Text(Localization.get("nav_home", currentLang), fontWeight = FontWeight.Bold) },
                    colors = navItemColors(),
                    modifier = Modifier.testTag("nav_item_home")
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.SCANNER || currentScreen == AppScreen.SCAN_RESULT,
                    onClick = { viewModel.navigateTo(AppScreen.SCANNER) },
                    icon = { Icon(imageVector = Icons.Default.Security, contentDescription = "Scan", modifier = Modifier.size(24.dp)) },
                    label = { Text(Localization.get("nav_scan", currentLang), fontWeight = FontWeight.Bold) },
                    colors = navItemColors(),
                    modifier = Modifier.testTag("nav_item_scan")
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.LEARN,
                    onClick = { viewModel.navigateTo(AppScreen.LEARN) },
                    icon = { Icon(imageVector = Icons.Default.School, contentDescription = "Learn", modifier = Modifier.size(24.dp)) },
                    label = { Text(Localization.get("nav_learn", currentLang), fontWeight = FontWeight.Bold) },
                    colors = navItemColors(),
                    modifier = Modifier.testTag("nav_item_learn")
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.COMMUNITY,
                    onClick = { viewModel.navigateTo(AppScreen.COMMUNITY) },
                    icon = { Icon(imageVector = Icons.Default.People, contentDescription = "Community", modifier = Modifier.size(24.dp)) },
                    label = { Text(Localization.get("nav_community", currentLang), fontWeight = FontWeight.Bold) },
                    colors = navItemColors(),
                    modifier = Modifier.testTag("nav_item_community")
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.SETTINGS,
                    onClick = { viewModel.navigateTo(AppScreen.SETTINGS) },
                    icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", modifier = Modifier.size(24.dp)) },
                    label = { Text(Localization.get("nav_profile", currentLang), fontWeight = FontWeight.Bold) },
                    colors = navItemColors(),
                    modifier = Modifier.testTag("nav_item_settings")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> HomeScreen(viewModel = viewModel, tts = tts)
                AppScreen.SCANNER -> ScannerScreen(viewModel = viewModel)
                AppScreen.PAUSE_PAY -> PauseBeforePayScreen(viewModel = viewModel, tts = tts)
                AppScreen.EMERGENCY_SOS -> EmergencySosScreen(viewModel = viewModel)
                AppScreen.LEARN -> LearnScreen(viewModel = viewModel, tts = tts)
                AppScreen.COMMUNITY -> CommunityScreen(viewModel = viewModel)
                AppScreen.SETTINGS -> SettingsScreen(viewModel = viewModel, tts = tts)
                AppScreen.SCAN_RESULT -> ScanResultScreen(viewModel = viewModel, tts = tts)
            }

            if (showVoiceDialog) {
                VoiceAssistantDialog(
                    viewModel = viewModel,
                    tts = tts,
                    onDismiss = { viewModel.closeVoiceAssistant() }
                )
            }
        }
    }
}

@Composable
private fun navItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = DeepPurple,
    selectedTextColor = DeepPurple,
    indicatorColor = LavenderContainer,
    unselectedIconColor = Color(0xFF7A7591),
    unselectedTextColor = Color(0xFF7A7591)
)
