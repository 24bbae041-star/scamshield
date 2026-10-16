package com.example.model

data class QuizScenario(
    val id: String,
    val category: String,
    val title: String,
    val scenarioText: String,
    val sender: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val badgeEarned: String
)

data class VolunteerProfile(
    val id: String,
    val name: String,
    val age: Int,
    val location: String,
    val languages: List<String>,
    val bio: String,
    val expertise: List<String>,
    val verifiedBadge: Boolean = true,
    val rating: Double,
    val totalSessions: Int,
    val availabilityNote: String
)

data class SafetyTip(
    val id: String,
    val title: String,
    val shortTip: String,
    val details: String,
    val tag: String
)

data class BankEmergencyContact(
    val bankName: String,
    val tollFreeNumber: String,
    val cardBlockNumber: String,
    val upiBlockSms: String,
    val officialWebsite: String
)

data class ScamPreset(
    val id: String,
    val title: String,
    val sender: String,
    val category: String, // Pension, KYC, UPI, Electricity, Job, Lottery, Courier, Police
    val content: String,
    val isScam: Boolean,
    val riskLevel: RiskLevel,
    val redFlags: List<String>,
    val safeExplanation: String,
    val safeVerification: String
)
