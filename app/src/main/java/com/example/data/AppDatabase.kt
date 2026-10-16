package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.model.ScanRecord
import com.example.model.TrustedContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ScanRecord::class, TrustedContact::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanRecordDao(): ScanRecordDao
    abstract fun trustedContactDao(): TrustedContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "scan_shield_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate initial pre-seeded safe demo contacts & scan history
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getInstance(context)
                                database.populateInitialData()
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun populateInitialData() {
        val contactDao = trustedContactDao()
        contactDao.insertContact(
            TrustedContact(
                name = "Aarav Sharma (Son)",
                relationship = "Son",
                phoneNumber = "+91 98765 43210",
                isPrimary = true,
                avatarEmoji = "👨‍💼",
                notes = "Lives nearby in Mumbai, always call first for any banking doubts"
            )
        )
        contactDao.insertContact(
            TrustedContact(
                name = "Priya Patel (Daughter)",
                relationship = "Daughter",
                phoneNumber = "+91 98123 45678",
                isPrimary = false,
                avatarEmoji = "👩‍⚕️",
                notes = "Available on WhatsApp video call"
            )
        )

        val scanDao = scanRecordDao()
        // 6 distinct initial scan-history entries as requested by prompt
        scanDao.insertScan(
            ScanRecord(
                type = "SCREENSHOT",
                title = "Fake Jeevan Pramaan App Link",
                snippet = "Dear Pensioner, update your Digital Life Certificate immediately via bit.ly/jeevan-apk or pension will stop this month.",
                riskLevel = "HIGH_RISK",
                evidenceListJson = "Urgent threat of pension stoppage||Suspicious bit.ly shortlink||Asking to install an untrusted APK outside Google Play||No government sender code (sent from +91-7649281920)",
                explanation = "The government never sends third-party bit.ly links or asks you to install APK files for Jeevan Pramaan. Digital Life Certificate is submitted only through the official Jeevan Pramaan app or your bank branch.",
                nextSteps = "Do not click the link or install the file. Block and report this number immediately. Visit your local bank or post office for authentic DLC submission.",
                independentVerification = "Verify on official portal: https://jeevanpramaan.gov.in/ or call National Pension Helpline: 1800-11-1965.",
                isSaved = true
            )
        )
        scanDao.insertScan(
            ScanRecord(
                type = "MESSAGE_TEXT",
                title = "Electricity Power Cut Alert",
                snippet = "Dear consumer your electricity power will be disconnected tonight at 9:30 PM from power office because your previous bill was not updated. Call 9821384912.",
                riskLevel = "HIGH_RISK",
                evidenceListJson = "Extremely tight artificial urgency (tonight at 9:30 PM)||Sent from personal 10-digit mobile number instead of official DISCOM utility SMS header||Instructs to call a personal number to settle bill",
                explanation = "State electricity boards never disconnect power within hours via SMS or direct you to call a personal mobile number. Fraudsters trick victims into installing remote desktop apps like AnyDesk.",
                nextSteps = "Ignore this message completely. Never call the number or share any OTP. Check your bill status directly on your official state electricity board app.",
                independentVerification = "Check your official consumer number directly on your state utility portal (e.g., Mahavitaran, BESCOM, TANGEDCO, Tata Power).",
                isSaved = true
            )
        )
        scanDao.insertScan(
            ScanRecord(
                type = "MESSAGE_TEXT",
                title = "SBI Pension Credit Alert",
                snippet = "Your A/C XX4592 has been credited with INR 34,250.00 on 01-Oct-26 by EPFO Pension. Avail Bal: INR 1,28,450.00. - SBI",
                riskLevel = "SAFE_VERIFIED",
                evidenceListJson = "Official verified alphanumeric SMS header (VK-SBIINB)||Contains masked account number (XX4592)||Contains no links, no phone numbers, and asks for no actions or PINs",
                explanation = "This is a standard informative bank credit notification. It does not ask you to click any links, verify any details, or call any unknown numbers.",
                nextSteps = "No action required. You can check your passbook or SBI YONO app at your own convenience.",
                independentVerification = "Call SBI official toll-free: 1800 1234 or visit your home branch.",
                isSaved = true
            )
        )
        scanDao.insertScan(
            ScanRecord(
                type = "UPI_LINK",
                title = "Army Officer OLX Advance Scam",
                snippet = "Sir, I am Major Rohit from Pune Cantonment. I want to buy your wooden dining table. I have sent QR code for Rs 15,000 advance payment. Scan and enter PIN to accept money.",
                riskLevel = "HIGH_RISK",
                evidenceListJson = "Military/Army impersonation to exploit respect and lower vigilance||Demands scanning a QR code to 'receive' money||Claims entering UPI PIN is required to get funds",
                explanation = "CRITICAL RULE: You NEVER enter your UPI PIN to receive money! Entering a PIN ALWAYS transfers money OUT of your bank account. The QR code is a collect-request trap.",
                nextSteps = "Do not scan the QR code and do not enter your UPI PIN. Block the buyer immediately on WhatsApp and OLX.",
                independentVerification = "National Cyber Crime Helpline: 1930.",
                isSaved = true
            )
        )
        scanDao.insertScan(
            ScanRecord(
                type = "PHONE_NUMBER",
                title = "Suspected Fake KYC Caller",
                snippet = "Call from +91-9871092819 claiming: 'Your SBI YONO account is deactivated due to pending Aadhaar biometric KYC. Provide Aadhaar number and OTP now.'",
                riskLevel = "HIGH_RISK",
                evidenceListJson = "Unsolicited voice call demanding banking OTP over phone||Threatens account deactivation||Impersonating SBI customer support",
                explanation = "Bank employees will NEVER ask you for your login password, debit card details, or OTP under any circumstance. Biometric KYC cannot be done over a phone call.",
                nextSteps = "Disconnect the call immediately. Do not share any 6-digit SMS code you receive. Contact your bank branch.",
                independentVerification = "Call SBI official 24x7 customer support: 1800 425 3800.",
                isSaved = true
            )
        )
        scanDao.insertScan(
            ScanRecord(
                type = "SCREENSHOT",
                title = "WhatsApp Telegram Work-From-Home",
                snippet = "Earn Rs 3,500 daily just by liking YouTube videos and rating Google Maps restaurants. No experience needed. Contact HR Anjali on Telegram @fast_earn_india.",
                riskLevel = "SUSPICIOUS",
                evidenceListJson = "Unrealistic daily payout for trivial tasks||Redirection to anonymous Telegram group||Classic task-based advance-fee fraud",
                explanation = "Scammers initially pay small sums of Rs 150 to build trust, then demand a deposit of Rs 10,000 to 'unlock' higher earnings before disappearing with your savings.",
                nextSteps = "Do not message them on Telegram. Block and report the sender on WhatsApp.",
                independentVerification = "Check official warnings on cybercrime.gov.in regarding Telegram task frauds.",
                isSaved = true
            )
        )
    }
}
