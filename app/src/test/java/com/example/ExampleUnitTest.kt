package com.example

import com.example.ai.GeminiScamScanner
import com.example.data.AppLanguage
import com.example.data.Localization
import com.example.data.ScamDatabase
import com.example.model.RiskLevel
import com.example.model.ScanType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun verifyScamPresetsCountAndUniqueness() {
        val presets = ScamDatabase.SCAM_PRESETS
        assertTrue("Must contain at least 12 scam presets", presets.size >= 12)

        val ids = presets.map { it.id }.toSet()
        assertEquals("All scam preset IDs must be unique", presets.size, ids.size)

        val titles = presets.map { it.title }.toSet()
        assertEquals("All scam preset titles must be unique", presets.size, titles.size)

        presets.forEach { preset ->
            assertTrue("Scam preset must be marked as scam", preset.isScam)
            assertTrue("Scam preset must have red flags", preset.redFlags.isNotEmpty())
            assertTrue("Scam preset must have safe explanation", preset.safeExplanation.isNotBlank())
        }
    }

    @Test
    fun verifyBenignPresetsCountAndUniqueness() {
        val benign = ScamDatabase.BENIGN_PRESETS
        assertTrue("Must contain at least 4 benign presets", benign.size >= 4)

        val ids = benign.map { it.id }.toSet()
        assertEquals("All benign preset IDs must be unique", benign.size, ids.size)

        benign.forEach { preset ->
            assertFalse("Benign preset must not be marked as scam", preset.isScam)
            assertEquals("Benign preset risk level must be safe", RiskLevel.SAFE_VERIFIED, preset.riskLevel)
        }
    }

    @Test
    fun verifyQuizScenariosCountAndIntegrity() {
        val quizzes = ScamDatabase.QUIZ_SCENARIOS
        assertTrue("Must contain at least 12 quiz scenarios", quizzes.size >= 12)

        val ids = quizzes.map { it.id }.toSet()
        assertEquals("All quiz IDs must be unique", quizzes.size, ids.size)

        quizzes.forEach { quiz ->
            assertTrue("Quiz options must have at least 2 choices", quiz.options.size >= 2)
            assertTrue("Correct index must be within options range", quiz.correctIndex in 0 until quiz.options.size)
            assertTrue("Quiz must award a badge", quiz.badgeEarned.isNotBlank())
            assertTrue("Quiz must have an educational explanation", quiz.explanation.isNotBlank())
        }
    }

    @Test
    fun verifyVolunteerProfiles() {
        val volunteers = ScamDatabase.VOLUNTEERS
        assertEquals("Must contain 5 unique volunteer profiles", 5, volunteers.size)

        val ids = volunteers.map { it.id }.toSet()
        assertEquals("All volunteer IDs must be unique", 5, ids.size)

        volunteers.forEach { volunteer ->
            assertTrue("Volunteer must speak at least 2 languages", volunteer.languages.size >= 2)
            assertTrue("Volunteer rating must be positive", volunteer.rating > 4.0)
        }
    }

    @Test
    fun verifyAllNineLanguagesSupported() {
        val languages = AppLanguage.values()
        assertEquals("Must support 9 languages", 9, languages.size)

        languages.forEach { lang ->
            val greeting = Localization.get("greeting", lang)
            assertNotNull(greeting)
            assertTrue(greeting.isNotBlank())

            val pauseHeading = Localization.get("pause_heading", lang)
            assertNotNull(pauseHeading)
            assertTrue(pauseHeading.isNotBlank())

            val upiRule = Localization.get("upi_pin_rule", lang)
            assertNotNull(upiRule)
            assertTrue(upiRule.isNotBlank())
        }
    }

    @Test
    fun testHeuristicClassificationForCriticalScams() = runBlocking {
        // 1. UPI PIN trap
        val upiResult = GeminiScamScanner.analyzeContent(
            inputText = "Scan this QR code and enter your UPI PIN to receive Rs 5000",
            bitmap = null,
            scanType = ScanType.MESSAGE_TEXT,
            language = AppLanguage.EN
        )
        assertEquals("UPI PIN to receive money must be HIGH_RISK", RiskLevel.HIGH_RISK.name, upiResult.riskLevel)

        // 2. Fake Jeevan Pramaan stoppage
        val jeevanResult = GeminiScamScanner.analyzeContent(
            inputText = "Dear pensioner submit your Jeevan Pramaan DLC or pension will be blocked bit.ly/dlc-apk",
            bitmap = null,
            scanType = ScanType.MESSAGE_TEXT,
            language = AppLanguage.EN
        )
        assertEquals("Fake pension stoppage must be HIGH_RISK", RiskLevel.HIGH_RISK.name, jeevanResult.riskLevel)

        // 3. AnyDesk remote control
        val anydeskResult = GeminiScamScanner.analyzeContent(
            inputText = "Please install AnyDesk from Play Store and give 9 digit code for KYC",
            bitmap = null,
            scanType = ScanType.MESSAGE_TEXT,
            language = AppLanguage.EN
        )
        assertEquals("AnyDesk installation must be HIGH_RISK", RiskLevel.HIGH_RISK.name, anydeskResult.riskLevel)
    }
}
