package com.example.ui

import android.app.Application
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiScamScanner
import com.example.data.AppDatabase
import com.example.data.AppLanguage
import com.example.data.Localization
import com.example.data.ScamDatabase
import com.example.data.FirestoreRepository
import com.example.model.QuizScenario
import com.example.model.RiskLevel
import com.example.model.ScanRecord
import com.example.model.ScanType
import com.example.model.ScamPreset
import com.example.model.TrustedContact
import com.example.ui.theme.SeniorTextScale
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppScreen {
    HOME,
    SCANNER,
    PAUSE_PAY,
    EMERGENCY_SOS,
    LEARN,
    COMMUNITY,
    SETTINGS,
    SCAN_RESULT
}

class ScanShieldViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val scanDao = database.scanRecordDao()
    private val contactDao = database.trustedContactDao()
    private val firestoreRepo = FirestoreRepository.create(application)

    private val _currentUser = MutableStateFlow<FirebaseUser?>(Firebase.auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        Firebase.auth.addAuthStateListener { auth ->
            _currentUser.value = auth.currentUser
        }
    }

    // Preferences & Accessibility States
    private val _currentLanguage = MutableStateFlow(AppLanguage.EN)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    private val _textScale = MutableStateFlow(SeniorTextScale.NORMAL)
    val textScale: StateFlow<SeniorTextScale> = _textScale.asStateFlow()

    private val _highContrast = MutableStateFlow(false)
    val highContrast: StateFlow<Boolean> = _highContrast.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _speechSpeed = MutableStateFlow(0.85f) // Slightly slower by default for clear senior comprehension
    val speechSpeed: StateFlow<Float> = _speechSpeed.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    // Navigation State
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<AppScreen>()

    // Scanner & Active Result
    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _activeScanRecord = MutableStateFlow<ScanRecord?>(null)
    val activeScanRecord: StateFlow<ScanRecord?> = _activeScanRecord.asStateFlow()

    // Daily Tip
    private val _dailyTipIndex = MutableStateFlow(0)
    val dailyTipIndex: StateFlow<Int> = _dailyTipIndex.asStateFlow()

    // Voice Assistant Dialog State
    private val _showVoiceDialog = MutableStateFlow(false)
    val showVoiceDialog: StateFlow<Boolean> = _showVoiceDialog.asStateFlow()

    private val _voiceDialogMessage = MutableStateFlow("Tap the microphone and ask me anything, or pick a question below.")
    val voiceDialogMessage: StateFlow<String> = _voiceDialogMessage.asStateFlow()

    // Quiz Progress & Badges State
    private val _completedQuizzes = MutableStateFlow<Map<String, Int>>(emptyMap())
    val completedQuizzes: StateFlow<Map<String, Int>> = _completedQuizzes.asStateFlow()

    private val _earnedBadges = MutableStateFlow<Set<String>>(emptySet())
    val earnedBadges: StateFlow<Set<String>> = _earnedBadges.asStateFlow()

    // Database reactive streams
    val scanHistory: StateFlow<List<ScanRecord>> = scanDao.getAllScans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val trustedContacts: StateFlow<List<TrustedContact>> = contactDao.getAllContacts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        return if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.lastIndex)
            true
        } else if (_currentScreen.value != AppScreen.HOME) {
            _currentScreen.value = AppScreen.HOME
            true
        } else {
            false
        }
    }

    fun switchLanguage(lang: AppLanguage) {
        _currentLanguage.value = lang
    }

    fun setTextScale(scale: SeniorTextScale) {
        _textScale.value = scale
    }

    fun toggleHighContrast(enabled: Boolean) {
        _highContrast.value = enabled
    }

    fun toggleDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }

    fun setSpeechSpeed(speed: Float) {
        _speechSpeed.value = speed
    }

    fun nextDailyTip() {
        _dailyTipIndex.value = (_dailyTipIndex.value + 1) % ScamDatabase.SAFETY_TIPS.size
    }

    fun openVoiceAssistant(customPrompt: String? = null) {
        if (customPrompt != null) {
            _voiceDialogMessage.value = customPrompt
        }
        _showVoiceDialog.value = true
    }

    fun closeVoiceAssistant() {
        _showVoiceDialog.value = false
    }

    fun analyzeContent(inputText: String, bitmap: Bitmap?, scanType: ScanType) {
        viewModelScope.launch {
            _isScanning.value = true
            try {
                val record = GeminiScamScanner.analyzeContent(
                    inputText = inputText,
                    bitmap = bitmap,
                    scanType = scanType,
                    language = _currentLanguage.value
                )
                // Save to Room database
                val insertedId = scanDao.insertScan(record)
                val savedRecord = record.copy(id = insertedId)
                _activeScanRecord.value = savedRecord
                firestoreRepo.saveScanToCloud(savedRecord)
                navigateTo(AppScreen.SCAN_RESULT)
            } catch (e: Exception) {
                Log.e("ScanShieldVM", "Scan error", e)
            } finally {
                _isScanning.value = false
            }
        }
    }

    fun loadPresetScan(preset: ScamPreset) {
        viewModelScope.launch {
            _isScanning.value = true
            val record = ScanRecord(
                type = ScanType.MESSAGE_TEXT.name,
                title = preset.title,
                snippet = preset.content,
                riskLevel = preset.riskLevel.name,
                evidenceListJson = preset.redFlags.joinToString("||"),
                explanation = preset.safeExplanation,
                nextSteps = if (preset.isScam) "Do not click, pay, or reply. Block sender and verify with official department." else "Safe to proceed. Standard informative update.",
                independentVerification = preset.safeVerification,
                isSaved = true
            )
            val insertedId = scanDao.insertScan(record)
            val savedRecord = record.copy(id = insertedId)
            _activeScanRecord.value = savedRecord
            firestoreRepo.saveScanToCloud(savedRecord)
            _isScanning.value = false
            navigateTo(AppScreen.SCAN_RESULT)
        }
    }

    fun deleteScanRecord(record: ScanRecord) {
        viewModelScope.launch {
            scanDao.deleteScan(record)
            if (_activeScanRecord.value?.id == record.id) {
                _activeScanRecord.value = null
                navigateBack()
            }
        }
    }

    fun showScanDetails(record: ScanRecord) {
        _activeScanRecord.value = record
        navigateTo(AppScreen.SCAN_RESULT)
    }

    fun addTrustedContact(name: String, relation: String, phone: String, isPrimary: Boolean) {
        viewModelScope.launch {
            if (isPrimary) {
                contactDao.resetPrimaryFlags()
            }
            val contact = TrustedContact(
                name = name,
                relationship = relation,
                phoneNumber = phone,
                isPrimary = isPrimary,
                avatarEmoji = when (relation.lowercase()) {
                    "son" -> "👨‍💼"
                    "daughter" -> "👩‍⚕️"
                    "spouse", "wife", "husband" -> "❤️"
                    "grandchild" -> "👧"
                    else -> "💜"
                }
            )
            val id = contactDao.insertContact(contact)
            firestoreRepo.saveContactToCloud(contact.copy(id = id))
        }
    }

    fun deleteTrustedContact(contact: TrustedContact) {
        viewModelScope.launch {
            contactDao.deleteContact(contact)
            firestoreRepo.deleteContactFromCloud(contact.id)
        }
    }

    fun setPrimaryContact(contactId: Long) {
        viewModelScope.launch {
            contactDao.resetPrimaryFlags()
            contactDao.setPrimaryContact(contactId)
        }
    }

    fun answerQuiz(scenario: QuizScenario, selectedOptionIndex: Int) {
        val updatedMap = _completedQuizzes.value.toMutableMap()
        updatedMap[scenario.id] = selectedOptionIndex
        _completedQuizzes.value = updatedMap

        if (selectedOptionIndex == scenario.correctIndex) {
            val updatedBadges = _earnedBadges.value.toMutableSet()
            updatedBadges.add(scenario.badgeEarned)
            _earnedBadges.value = updatedBadges
        }
    }

    fun speakText(tts: TextToSpeech?, text: String) {
        if (tts == null) return
        _isSpeaking.value = true
        tts.setSpeechRate(_speechSpeed.value)
        val langLocale = when (_currentLanguage.value) {
            AppLanguage.HI -> Locale("hi", "IN")
            AppLanguage.GU -> Locale("gu", "IN")
            AppLanguage.TA -> Locale("ta", "IN")
            AppLanguage.BN -> Locale("bn", "IN")
            AppLanguage.MR -> Locale("mr", "IN")
            AppLanguage.TE -> Locale("te", "IN")
            AppLanguage.KN -> Locale("kn", "IN")
            AppLanguage.ML -> Locale("ml", "IN")
            else -> Locale("en", "IN")
        }
        val result = tts.setLanguage(langLocale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts.language = Locale.ENGLISH
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ScanShieldTTS")
    }

    fun stopSpeaking(tts: TextToSpeech?) {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun clearAllData() {
        viewModelScope.launch {
            scanDao.clearAllScans()
            contactDao.clearAllContacts()
            _completedQuizzes.value = emptyMap()
            _earnedBadges.value = emptySet()
            _activeScanRecord.value = null
            navigateTo(AppScreen.HOME)
        }
    }
}
