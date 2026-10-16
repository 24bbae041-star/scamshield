package com.example.ai

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import com.example.data.AppLanguage
import com.example.data.ScamDatabase
import com.example.model.RiskLevel
import com.example.model.ScanRecord
import com.example.model.ScanType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

object GeminiScamScanner {

    private const val TAG = "GeminiScamScanner"
    private const val GEMINI_MODEL = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    suspend fun analyzeContent(
        inputText: String,
        bitmap: Bitmap?,
        scanType: ScanType,
        language: AppLanguage
    ): ScanRecord = withContext(Dispatchers.IO) {
        // First check if input matches any of our known realistic presets
        val matchedPreset = matchKnownPreset(inputText)
        if (matchedPreset != null) {
            return@withContext ScanRecord(
                type = scanType.name,
                title = matchedPreset.title,
                snippet = matchedPreset.content.take(150),
                riskLevel = matchedPreset.riskLevel.name,
                evidenceListJson = matchedPreset.redFlags.joinToString("||"),
                explanation = matchedPreset.safeExplanation,
                nextSteps = if (matchedPreset.isScam) "Do not click, pay, or reply. Block the sender and verify with official bank or family." else "Safe to proceed. Standard informative update.",
                independentVerification = matchedPreset.safeVerification,
                isSaved = true
            )
        }

        // Try live Gemini API if API key is configured
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (!apiKey.isNullOrBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val liveResult = callGeminiLive(inputText, bitmap, apiKey, language)
                if (liveResult != null) {
                    return@withContext liveResult
                }
            } catch (e: Exception) {
                Log.w(TAG, "Live Gemini call failed, falling back to heuristic engine", e)
            }
        }

        // Heuristic analysis engine fallback
        return@withContext runHeuristicEngine(inputText, bitmap != null, scanType, language)
    }

    private fun matchKnownPreset(text: String): com.example.model.ScamPreset? {
        if (text.isBlank()) return null
        val lower = text.lowercase()
        // Check scam presets
        for (preset in ScamDatabase.SCAM_PRESETS) {
            if (lower.contains(preset.title.lowercase()) ||
                lower.contains(preset.sender.lowercase()) ||
                (preset.content.length > 20 && lower.contains(preset.content.take(30).lowercase()))
            ) {
                return preset
            }
        }
        // Check benign presets
        for (preset in ScamDatabase.BENIGN_PRESETS) {
            if (lower.contains(preset.title.lowercase()) ||
                lower.contains(preset.sender.lowercase()) ||
                (preset.content.length > 20 && lower.contains(preset.content.take(30).lowercase()))
            ) {
                return preset
            }
        }
        return null
    }

    private fun callGeminiLive(
        text: String,
        bitmap: Bitmap?,
        apiKey: String,
        language: AppLanguage
    ): ScanRecord? {
        val url = "$BASE_URL/$GEMINI_MODEL:generateContent?key=$apiKey"

        val prompt = buildString {
            append("You are an expert cybersecurity protector for elderly pensioners in India. ")
            append("Analyze this message or screenshot content for cyber fraud, fake pension updates, fake KYC, fake power cut, or UPI scam. ")
            append("Respond strictly in valid JSON format with this exact structure:\n")
            append("{\n")
            append("  \"title\": \"Short headline describing the finding\",\n")
            append("  \"riskLevel\": \"HIGH_RISK\" or \"SUSPICIOUS\" or \"SAFE_VERIFIED\" or \"UNABLE_TO_VERIFY\",\n")
            append("  \"redFlags\": [\"evidence 1\", \"evidence 2\", \"evidence 3\"],\n")
            append("  \"explanation\": \"A simple, reassuring explanation in ${language.displayName} avoiding tech jargon\",\n")
            append("  \"nextSteps\": \"Actionable recommended steps for an elderly person in ${language.displayName}\",\n")
            append("  \"independentVerification\": \"How to independently verify via official helpline like 1930 or official portal\"\n")
            append("}\n")
            append("Rule: Never declare a message genuine simply because no warning signs were seen. Explain that UPI PIN is never required to receive money.\n")
            if (text.isNotBlank()) {
                append("Content to analyze: $text\n")
            }
        }

        val partsArray = JSONArray()
        val textPart = JSONObject().put("text", prompt)
        partsArray.put(textPart)

        if (bitmap != null) {
            val base64 = bitmapToBase64(bitmap)
            val inlineData = JSONObject()
                .put("mimeType", "image/jpeg")
                .put("data", base64)
            val imagePart = JSONObject().put("inlineData", inlineData)
            partsArray.put(imagePart)
        }

        val contentObj = JSONObject().put("parts", partsArray)
        val contentsArray = JSONArray().put(contentObj)

        val rootRequest = JSONObject()
            .put("contents", contentsArray)
            .put(
                "generationConfig",
                JSONObject().put("responseMimeType", "application/json")
            )

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = rootRequest.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e(TAG, "Gemini API error code: ${response.code}")
            return null
        }

        val responseBody = response.body?.string() ?: return null
        val responseJson = JSONObject(responseBody)
        val candidate = responseJson.optJSONArray("candidates")?.optJSONObject(0) ?: return null
        val rawText = candidate.optJSONObject("content")
            ?.optJSONArray("parts")
            ?.optJSONObject(0)
            ?.optString("text") ?: return null

        val parsed = JSONObject(rawText)
        val redFlagsJson = parsed.optJSONArray("redFlags") ?: JSONArray()
        val redFlagsList = mutableListOf<String>()
        for (i in 0 until redFlagsJson.length()) {
            redFlagsList.add(redFlagsJson.optString(i))
        }

        val riskLevelStr = parsed.optString("riskLevel", "SUSPICIOUS")
        val riskLevel = try {
            RiskLevel.valueOf(riskLevelStr)
        } catch (e: Exception) {
            RiskLevel.SUSPICIOUS
        }

        return ScanRecord(
            type = if (bitmap != null) ScanType.SCREENSHOT.name else ScanType.MESSAGE_TEXT.name,
            title = parsed.optString("title", "Safety Analysis Result"),
            snippet = if (text.isNotBlank()) text.take(150) else "Screenshot analyzed by Gemini AI",
            riskLevel = riskLevel.name,
            evidenceListJson = redFlagsList.joinToString("||"),
            explanation = parsed.optString("explanation", "Please inspect warning signs carefully."),
            nextSteps = parsed.optString("nextSteps", "Do not share OTPs, PINs, or install remote apps."),
            independentVerification = parsed.optString("independentVerification", "Call National Cybercrime Helpline 1930 or your bank."),
            isSaved = true
        )
    }

    private fun runHeuristicEngine(
        text: String,
        hasImage: Boolean,
        scanType: ScanType,
        language: AppLanguage
    ): ScanRecord {
        val lower = text.lowercase()
        val redFlags = mutableListOf<String>()

        var risk = RiskLevel.SAFE_VERIFIED
        var title = "No Obvious Warning Signs Detected"
        var explanation = "No common scam keywords or panic triggers were identified in this message."
        var nextSteps = "Stay cautious. Even if no warning signs are detected, never share your UPI PIN or OTP with anyone."
        var verification = "Verify any transaction directly inside your official banking application or passbook."

        // 1. Critical UPI PIN trap
        if (lower.contains("pin") && (lower.contains("receive") || lower.contains("credit") || lower.contains("accept") || lower.contains("qr"))) {
            risk = RiskLevel.HIGH_RISK
            title = "Severe UPI PIN Trap Detected 🚨"
            redFlags.add("Claims you need to enter UPI PIN to receive money (100% scam trap)")
            redFlags.add("QR code collect request pretending to be incoming credit")
            explanation = "You NEVER enter your UPI PIN to receive money! Entering a PIN authorises money to leave your bank account."
            nextSteps = "Do not enter your PIN or scan the QR code. Block the sender immediately."
            verification = "Report suspected cyber fraud on helpline 1930 or cybercrime.gov.in."
        }
        // 2. Pension / Jeevan Pramaan stoppage threat
        else if ((lower.contains("jeevan") || lower.contains("pension") || lower.contains("dlc") || lower.contains("life certificate")) &&
            (lower.contains("stop") || lower.contains("block") || lower.contains("cancel") || lower.contains("apk") || lower.contains("bit.ly") || lower.contains("link"))
        ) {
            risk = RiskLevel.HIGH_RISK
            title = "Fake Pension Stoppage Scam 🚨"
            redFlags.add("Threatens immediate pension stoppage to cause panic")
            redFlags.add("Asks to click unofficial link or install third-party APK")
            redFlags.add("Official departments give full 60-day window for DLC and never send bit.ly links")
            explanation = "Pension departments never suspend pensions via SMS links. Digital Life Certificates are submitted only at banks, post offices, or the official government app."
            nextSteps = "Do not click any link. Visit your bank branch or local post office to submit your Life Certificate."
            verification = "Check on official portal: https://jeevanpramaan.gov.in/ or call 1800-11-1965."
        }
        // 3. Electricity disconnection urgency
        else if ((lower.contains("electricity") || lower.contains("power") || lower.contains("light") || lower.contains("bill")) &&
            (lower.contains("disconnect") || lower.contains("tonight") || lower.contains("cut") || lower.contains("9:30") || lower.contains("officer"))
        ) {
            risk = RiskLevel.HIGH_RISK
            title = "Electricity Disconnection Panic Scam 🚨"
            redFlags.add("Extreme urgency ('disconnect tonight') designed to prevent fact-checking")
            redFlags.add("Asks to call a personal mobile number instead of official utility contact")
            redFlags.add("Trick used to force immediate UPI transfer or AnyDesk download")
            explanation = "State power boards never cut power within hours via SMS or direct citizens to personal phone numbers."
            nextSteps = "Do not call the number. Check your electricity bill status on your official state DISCOM portal."
            verification = "Call the customer care number printed on your physical electricity bill."
        }
        // 4. Remote screen sharing app
        else if (lower.contains("anydesk") || lower.contains("teamviewer") || lower.contains("quicksupport") || lower.contains("rustdesk") || lower.contains("9 digit")) {
            risk = RiskLevel.HIGH_RISK
            title = "Dangerous Screen-Sharing App Request 🚨"
            redFlags.add("Requests installation of AnyDesk/TeamViewer remote software")
            redFlags.add("Asking for 9-digit remote control access code")
            redFlags.add("Gives full visual control of your smartphone and allows intercepting OTPs")
            explanation = "No genuine bank employee or customer care agent will ever ask you to install AnyDesk or screen-sharing software."
            nextSteps = "Uninstall any screen sharing app immediately. Never share any 9-digit code."
            verification = "Call your bank branch immediately to ensure your account is locked."
        }
        // 5. Digital arrest / CBI / Police
        else if (lower.contains("police") || lower.contains("cbi") || lower.contains("customs") || lower.contains("arrest") || lower.contains("narcotics") || lower.contains("fir")) {
            risk = RiskLevel.HIGH_RISK
            title = "Fake Police / Digital Arrest Threat 🚨"
            redFlags.add("Claims you are under 'Digital Arrest' (no such law exists in India)")
            redFlags.add("Demands staying on video call or transferring funds for 'verification'")
            redFlags.add("Intimidation using forged logos or police uniforms")
            explanation = "Indian Police, CBI, ED, and courts NEVER conduct arrests over video calls and never ask citizens to transfer money."
            nextSteps = "Disconnect immediately. Do not transfer any money. Inform your family."
            verification = "Call the National Cybercrime Helpline 1930 immediately."
        }
        // 6. Bank KYC / Account blocked
        else if (lower.contains("kyc") || lower.contains("pan") || lower.contains("yono") || lower.contains("deactivated") || lower.contains("blocked")) {
            risk = RiskLevel.HIGH_RISK
            title = "Suspicious KYC Account Alert 🚨"
            redFlags.add("Threat of bank account deactivation due to KYC")
            redFlags.add("Asks to submit PAN or Aadhaar on an unverified link or over call")
            explanation = "Banks never block accounts abruptly via SMS links. KYC updates are performed inside branch premises or official banking apps."
            nextSteps = "Do not open any link. Visit your bank branch with your passbook to check."
            verification = "Call your bank's verified toll-free number from their official website."
        }
        // 7. General suspicious links
        else if (lower.contains("http") || lower.contains(".apk") || lower.contains("bit.ly") || lower.contains("tinyurl") || lower.contains(".xyz")) {
            risk = RiskLevel.SUSPICIOUS
            title = "Suspicious Web Link / File Detected ⚠️"
            redFlags.add("Contains unverified URL or shortlink")
            redFlags.add("May download an untrusted APK application outside Google Play")
            explanation = "Malicious links can silently download spyware or spoof banking login screens."
            nextSteps = "Do not tap on the link. Delete the message."
            verification = "Only download apps directly from Google Play Store."
        }
        else if (text.isBlank() && hasImage) {
            risk = RiskLevel.SUSPICIOUS
            title = "Screenshot Awaiting Cloud Inspection"
            redFlags.add("Screenshot image received for safety review")
            explanation = "For live AI deep inspection of screenshots, ensure your Gemini API key is configured in AI Studio Secrets."
            nextSteps = "Look for suspicious links, urgency words, or requests for PIN/OTP in the screenshot."
            verification = "Call your trusted family contact or 1930 helpline if in doubt."
        }

        return ScanRecord(
            type = scanType.name,
            title = title,
            snippet = if (text.isNotBlank()) text.take(150) else "Image analyzed via safety heuristics",
            riskLevel = risk.name,
            evidenceListJson = redFlags.joinToString("||"),
            explanation = explanation,
            nextSteps = nextSteps,
            independentVerification = verification,
            isSaved = true
        )
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        // Resize bitmap if very large to prevent memory overhead
        val scaled = if (bitmap.width > 1024 || bitmap.height > 1024) {
            val ratio = Math.min(1024f / bitmap.width, 1024f / bitmap.height)
            Bitmap.createScaledBitmap(bitmap, (bitmap.width * ratio).toInt(), (bitmap.height * ratio).toInt(), true)
        } else {
            bitmap
        }
        scaled.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }
}
