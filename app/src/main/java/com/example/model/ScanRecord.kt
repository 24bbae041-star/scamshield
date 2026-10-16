package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class RiskLevel(val label: String, val emoji: String) {
    HIGH_RISK("High Risk", "🚨"),
    SUSPICIOUS("Suspicious", "⚠️"),
    SAFE_VERIFIED("No Obvious Warning Signs", "✅"),
    UNABLE_TO_VERIFY("Unable to Verify", "❓")
}

enum class ScanType {
    SCREENSHOT,
    MESSAGE_TEXT,
    PHONE_NUMBER,
    UPI_LINK
}

@Entity(tableName = "scan_records")
data class ScanRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val type: String, // ScanType name
    val title: String,
    val snippet: String,
    val riskLevel: String, // RiskLevel name
    val evidenceListJson: String, // pipe or newline separated list
    val explanation: String,
    val nextSteps: String,
    val independentVerification: String,
    val isSaved: Boolean = true
) {
    fun getEvidenceItems(): List<String> {
        return if (evidenceListJson.isBlank()) emptyList()
        else evidenceListJson.split("||").map { it.trim() }.filter { it.isNotEmpty() }
    }
}
