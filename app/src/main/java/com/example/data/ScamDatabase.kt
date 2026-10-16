package com.example.data

import com.example.model.BankEmergencyContact
import com.example.model.QuizScenario
import com.example.model.RiskLevel
import com.example.model.SafetyTip
import com.example.model.ScamPreset
import com.example.model.VolunteerProfile

object ScamDatabase {

    val SCAM_PRESETS: List<ScamPreset> = listOf(
        // 1. Fake Pension DLC APK
        ScamPreset(
            id = "scam_1",
            title = "Fake Jeevan Pramaan App Link",
            sender = "+91-7649281920",
            category = "Pension",
            content = "Dear Pensioner, update your Digital Life Certificate immediately via bit.ly/jeevan-dlc-2026 or your monthly pension will be suspended this month.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Urgent threat of pension stoppage",
                "Unverified bit.ly shortlink leading to an APK file",
                "Sent from an unknown private 10-digit mobile number",
                "Demands immediate action without formal bank letter"
            ),
            safeExplanation = "Official Digital Life Certificates (Jeevan Pramaan) are only submitted via the official Jeevan Pramaan government app, Face RD biometric app from Google Play, or in person at your bank branch/Post Office. Government departments never send bit.ly download links.",
            safeVerification = "Visit official government site https://jeevanpramaan.gov.in/ or call National Pension Helpline 1800-11-1965."
        ),
        // 2. Electricity Disconnection Threat
        ScamPreset(
            id = "scam_2",
            title = "Electricity Power Cut Alert",
            sender = "+91-9821384912",
            category = "Electricity",
            content = "Dear consumer, your electricity power will be disconnected tonight at 9:30 PM from power office because your previous month bill was not updated. Immediately contact power officer Sharma at 9821384912.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Artificial urgency (tonight at 9:30 PM)",
                "Sent from a personal mobile number, not official DISCOM utility header (like VK-MSEB or AD-BESCOM)",
                "Directs you to call a private mobile number to resolve"
            ),
            safeExplanation = "DISCOMs never cut off power within hours via SMS or direct you to call a personal phone number. Scammers use this panic to get seniors to download AnyDesk or make an urgent UPI payment to their personal account.",
            safeVerification = "Check your current bill on your official state electricity board app or customer care portal."
        ),
        // 3. SBI YONO Account Deactivation
        ScamPreset(
            id = "scam_3",
            title = "SBI YONO Account Blocked Alert",
            sender = "DM-SBIOFR",
            category = "KYC",
            content = "Dear Customer, your SBI YONO account will be blocked today. Please click http://sbi-kyc-verify-portal.in to update your PAN Card and Aadhaar to maintain active banking services.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Fake website URL (sbi-kyc-verify-portal.in instead of onlinesbi.sbi)",
                "Threat of immediate account blocking",
                "Requests PAN and Aadhaar details on an unverified web page"
            ),
            safeExplanation = "SBI never sends links via SMS to update PAN or Aadhaar. Banking KYC can only be done at your home branch or securely logged inside the official SBI YONO app from Google Play.",
            safeVerification = "Call SBI 24x7 Customer Care: 1800 1234 or visit your nearest SBI branch."
        ),
        // 4. Army Officer OLX QR Code Trap
        ScamPreset(
            id = "scam_4",
            title = "Army Officer OLX QR Scam",
            sender = "Major Rohit (+91-9923847291)",
            category = "UPI",
            content = "Sir, I am Major Rohit Sharma from Pune Military Camp. I want to buy your furniture. I have sent this QR code for Rs 12,500 advance payment. Please scan in Google Pay and enter your 6-digit PIN to receive money in your account.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Military impersonation to build false trust and discourage questioning",
                "Claims you must scan a QR code to 'receive' money",
                "Instructs you to enter your UPI PIN to accept payment"
            ),
            safeExplanation = "GOLDEN RULE: You NEVER enter your UPI PIN to receive money! Entering your UPI PIN authorises a transfer OUT of your account. Scanning a QR code sent by a buyer is always a payment request trap.",
            safeVerification = "Refuse immediately. Report the sender to OLX and block them on WhatsApp."
        ),
        // 5. 7th Pay Commission Pension Arrears Scam
        ScamPreset(
            id = "scam_5",
            title = "7th Pay Commission Arrears Bonus",
            sender = "+91-8849201948",
            category = "Pension",
            content = "Central Government Pensioners Welfare notification: Rs 48,500 7th Pay Commission arrears credited to your pending account. Pay Rs 750 processing tax on UPI id central.pension.tax@icici to release full amount.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Advance-fee scam: asks you to pay money to get money",
                "Personal UPI ID masquerading as government office",
                "Unsolicited claim of large pending pension bonus"
            ),
            safeExplanation = "Government pension disbursements, DA arrears, and revisions are directly credited to your pension bank account automatically by CPPC/treasury. No processing fee, registration charge, or tax deposit is ever required from pensioners.",
            safeVerification = "Check your official pension slip on SPARSH (for defence) or Bhavishya (for civil pensioners)."
        ),
        // 6. Digital Arrest / Fake Police CBI Video Call
        ScamPreset(
            id = "scam_6",
            title = "Digital Arrest by Fake Police/CBI",
            sender = "Inspector Ajay (+91-9128374650)",
            category = "Police",
            content = "A parcel containing fake passports and 150 grams MDMA seized at Mumbai customs under your Aadhaar name. You are under Digital Arrest. Stay on video call and transfer all bank balance to RBI verification account or police team will arrest you in 2 hours.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Fabricated concept of 'Digital Arrest' (no such legal power exists in India)",
                "Demands transferring money to a 'security' or 'RBI verification' account",
                "Extreme coercion, fear, and isolating you from family members"
            ),
            safeExplanation = "Indian law, State Police, CBI, ED, and Customs NEVER conduct arrests via Skype/WhatsApp video calls and NEVER ask citizens to transfer money for 'verification'. This is a notorious psychological cyber fraud.",
            safeVerification = "Disconnect immediately. Do NOT transfer a single rupee. Call the National Cyber Helpline 1930."
        ),
        // 7. KBC WhatsApp Lucky Draw Lottery
        ScamPreset(
            id = "scam_7",
            title = "KBC 25 Lakh WhatsApp Lottery",
            sender = "+92-301-9283741",
            category = "Lottery",
            content = "Congratulations! Your mobile number won Rs 25,00,000 in Kaun Banega Crorepati (KBC) WhatsApp Lucky Draw. Contact KBC Manager Rana Pratap on WhatsApp with your bank account details and deposit Rs 12,200 GST clearance fee.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Foreign country code (+92 Pakistan) claiming to be Mumbai KBC office",
                "Classic lottery scam where you never bought any lottery ticket",
                "Advance fee demanded to claim non-existent prize"
            ),
            safeExplanation = "Sony TV, KBC, and Big B never conduct any WhatsApp lotteries. You cannot win a lottery you never entered. Any demand for advance GST or registration charges is fraudulent.",
            safeVerification = "Delete message and report sender as spam on WhatsApp."
        ),
        // 8. Work-From-Home YouTube Task Fraud
        ScamPreset(
            id = "scam_8",
            title = "Part-Time YouTube Like Task",
            sender = "+91-9988776655",
            category = "Job",
            content = "Earn Rs 3,000 to Rs 8,000 daily from home! Just like YouTube videos and write reviews on Google Maps. We already sent Rs 150 bonus to your UPI. Join our VIP Telegram channel to invest in prepaid merchant tasks for 40% returns.",
            isScam = true,
            riskLevel = RiskLevel.SUSPICIOUS,
            redFlags = listOf(
                "Small initial payout used as bait to gain psychological compliance",
                "Requests joining private Telegram channel",
                "Demands prepaid deposit to unlock 'VIP returns'"
            ),
            safeExplanation = "Task scammers initially pay tiny sums of Rs 100-200 to establish trust, then persuade victims to invest lakhs into 'crypto tasks' on fake dashboards where the money can never be withdrawn.",
            safeVerification = "Check Ministry of Home Affairs warnings on cybercrime.gov.in regarding Telegram task scams."
        ),
        // 9. Fake Income Tax Refund Link
        ScamPreset(
            id = "scam_9",
            title = "Income Tax Refund Notification",
            sender = "AX-ITDEPT",
            category = "KYC",
            content = "Income Tax Department has approved your refund of INR 18,450. Please confirm your bank account number and debit card expiry/CVV at https://incometax-gov-in-refund.online to credit funds in 24 hours.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Fake lookalike domain (incometax-gov-in-refund.online instead of official incometax.gov.in)",
                "Requests highly confidential debit card expiry and 3-digit CVV",
                "Tax refunds never require card details or CVVs"
            ),
            safeExplanation = "Income tax refunds are directly ECS credited to your pre-validated bank account linked to your PAN. The IT department never asks for debit card numbers, PINs, or CVVs.",
            safeVerification = "Check your refund status directly on the official portal: https://www.incometax.gov.in/."
        ),
        // 10. Remote Access AnyDesk App Request
        ScamPreset(
            id = "scam_10",
            title = "Bank Support Remote Access Request",
            sender = "+91-9123456780",
            category = "KYC",
            content = "Bank Technical Officer calling: Your internet banking has technical error. Please install AnyDesk / QuickSupport from Play Store and provide 9-digit code so our technician can fix your pension account remotely.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Request to install remote screen-sharing software (AnyDesk / TeamViewer / QuickSupport)",
                "Demanding the 9-digit remote access code",
                "Allows scammer full view and control of your phone and OTPs"
            ),
            safeExplanation = "NO BANK OR OFFICIAL INSTITUTION EVER ASKS CUSTOMERS TO INSTALL ANYDESK OR SCREEN-SHARING APPS. Once you share the code, scammers see your banking passwords and intercept OTPs live.",
            safeVerification = "Never install remote desktop apps on advice of unknown callers. Disconnect immediately."
        ),
        // 11. Fake Relative in Emergency (Grandchild Impersonation)
        ScamPreset(
            id = "scam_11",
            title = "Grandchild Hospital Emergency",
            sender = "+91-7019283746",
            category = "UPI",
            content = "Dadu, my phone broke and I had a minor scooter accident. I am at city hospital and need Rs 20,000 urgently for medical tests. Please don't tell Mummy and Papa, just send on this doctor's UPI id dr.hospital98@okhdfcbank right now.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Urgent emotional blackmail exploiting love for grandchildren",
                "Explicit instruction: 'Please don't tell Mummy and Papa' to prevent verification",
                "Sent from an unfamiliar phone number"
            ),
            safeExplanation = "Scammers use social engineering and distress urgency. Always call the grandchild's known phone number or their parents directly before sending any money.",
            safeVerification = "Call your child or grandchild on their saved phone number immediately to confirm."
        ),
        // 12. Fake Courier Customs Parcel Notice
        ScamPreset(
            id = "scam_12",
            title = "FedEx Customs Parcel Seizure",
            sender = "+91-8899001122",
            category = "Courier",
            content = "FedEx Alert: Parcel #FDX-8921 to Taiwan under your name is detained by Delhi customs with contraband items. Press 1 to speak with Customs Narcotics Officer to avoid immediate FIR.",
            isScam = true,
            riskLevel = RiskLevel.HIGH_RISK,
            redFlags = listOf(
                "Interactive voice response (IVR) scam attempting to transfer call to fake cops",
                "Unsolicited parcel you never shipped",
                "Threat of immediate criminal FIR"
            ),
            safeExplanation = "Couriers do not connect you directly to police or customs officers via phone prompts. Legitimate customs notices are sent by registered post.",
            safeVerification = "Ignore the message and report on cybercrime.gov.in."
        )
    )

    val BENIGN_PRESETS: List<ScamPreset> = listOf(
        // 1. Genuine EPFO Pension Credit
        ScamPreset(
            id = "safe_1",
            title = "Genuine EPFO Pension Credit Alert",
            sender = "VK-EPFOHO",
            category = "Pension",
            content = "Dear Pensioner, Pension amount of Rs 28,450 for the month of Sep-2026 has been credited to your bank account ending in XX3821 through EPS-95. - EPFO",
            isScam = false,
            riskLevel = RiskLevel.SAFE_VERIFIED,
            redFlags = emptyList(),
            safeExplanation = "This is a legitimate government EPFO SMS alert with authentic departmental header (VK-EPFOHO), masked account number, and containing NO links, phone numbers, or requests for action.",
            safeVerification = "Check your bank passbook or EPFO Umang app at your leisure."
        ),
        // 2. Genuine Bank Transaction Notification
        ScamPreset(
            id = "safe_2",
            title = "Genuine Bank Debit Alert",
            sender = "VM-HDFCBK",
            category = "UPI",
            content = "Rs 450.00 debited from HDFC Bank A/c **9102 on 15-OCT-26 via UPI to APNA GROCERY STORE. UPI Ref 629102938491. If not done by you, SMS BLOCK to 5676712.",
            isScam = false,
            riskLevel = RiskLevel.SAFE_VERIFIED,
            redFlags = emptyList(),
            safeExplanation = "Legitimate standard bank transactional SMS with standard bank shortcode and an official SMS-based safety block option. Does not ask for sensitive info.",
            safeVerification = "Check transaction in HDFC NetBanking or Mobile Banking app."
        ),
        // 3. Genuine Aadhaar Authentication Receipt
        ScamPreset(
            id = "safe_3",
            title = "Genuine UIDAI Aadhaar Auth Alert",
            sender = "AD-UIDAI",
            category = "KYC",
            content = "Dear Resident, Aadhaar (ending with XXXX 4192) was used for fingerprint biometric authentication at Post Office DLC Centre on 14/10/2026 at 11:22 hrs. - UIDAI",
            isScam = false,
            riskLevel = RiskLevel.SAFE_VERIFIED,
            redFlags = emptyList(),
            safeExplanation = "Legitimate UIDAI security receipt confirming an in-person authentication conducted by you. Does not ask you to click any external link.",
            safeVerification = "Check your Aadhaar authentication history on myaadhaar.uidai.gov.in."
        ),
        // 4. Genuine LPG Cylinder Subsidy Credit
        ScamPreset(
            id = "safe_4",
            title = "Genuine LPG Subsidy Credit Alert",
            sender = "BP-BPCLPG",
            category = "Electricity",
            content = "Subsidy of Rs 200.00 for your Bharat Gas Booking Ref #789210 has been transferred to your Aadhaar-linked bank account on 12-Oct-26. - Bharat Gas",
            isScam = false,
            riskLevel = RiskLevel.SAFE_VERIFIED,
            redFlags = emptyList(),
            safeExplanation = "Legitimate standard subsidy credit notification from Bharat Petroleum with authentic commercial header and valid booking reference.",
            safeVerification = "Verify with your local gas agency or bank statement."
        )
    )

    val QUIZ_SCENARIOS: List<QuizScenario> = listOf(
        QuizScenario(
            id = "quiz_1",
            category = "Pension",
            title = "Digital Life Certificate Deadline",
            scenarioText = "You receive an SMS: 'Your pension will be cancelled in 48 hours unless you submit Jeevan Pramaan by clicking this link: bit.ly/dlc-fast'. What should you do?",
            sender = "Unknown +91-9872109281",
            options = listOf(
                "Click the link quickly before 48 hours run out",
                "Delete the message and submit DLC at your official bank branch or Jeevan Pramaan app",
                "Forward the link to friends so they also don't lose pension",
                "Reply to SMS asking if they are genuine"
            ),
            correctIndex = 1,
            explanation = "Correct! Never click bit.ly or third-party links for pension certificates. The government gives you the full months of October and November, and DLC can be safely submitted at your local post office, bank branch, or the official government app.",
            badgeEarned = "🛡️ Pension Guardian"
        ),
        QuizScenario(
            id = "quiz_2",
            category = "UPI",
            title = "Receiving Money via UPI QR Code",
            scenarioText = "Someone buying your old sofa online sends you a QR code on WhatsApp and says: 'Scan this QR code and enter your 6-digit UPI PIN to receive the Rs 8,000 in your account.' What is the truth?",
            sender = "Buyer on WhatsApp",
            options = listOf(
                "Entering your PIN is needed to confirm your identity to get money",
                "You NEVER enter a UPI PIN to receive money — entering a PIN pays money OUT",
                "Scan the QR code but enter a wrong PIN first to test",
                "Send them an OTP instead"
            ),
            correctIndex = 1,
            explanation = "Spot on! The most important rule of UPI: You NEVER enter your UPI PIN to receive money. Entering your PIN ALWAYS authorises money to leave your bank account.",
            badgeEarned = "🏅 UPI Master"
        ),
        QuizScenario(
            id = "quiz_3",
            category = "Electricity",
            title = "Tonight 9:30 PM Power Cut Threat",
            scenarioText = "An SMS says your power will be cut tonight at 9:30 PM due to an unpaid bill, asking you to call 'Power Officer Sharma' on his personal mobile number immediately. How do you respond?",
            sender = "+91-9821384912",
            options = listOf(
                "Call Sharma immediately and pay via Google Pay to prevent power cut",
                "Ignore the SMS and check your bill on the official state electricity portal",
                "Share your meter number and Aadhaar card on WhatsApp",
                "Ask your neighbor to pay for you"
            ),
            correctIndex = 1,
            explanation = "Great job! Electricity boards never disconnect power within hours via SMS or direct you to personal phone numbers. This is a common fraud to force panic UPI transfers.",
            badgeEarned = "⚡ Utility Shield"
        ),
        QuizScenario(
            id = "quiz_4",
            category = "Police",
            title = "CBI Video Call / Digital Arrest",
            scenarioText = "A man wearing a police uniform video calls you claiming your Aadhaar was used in a drug smuggling case. He says you are under 'Digital Arrest' and must transfer Rs 2 lakhs to an RBI verification account to prove innocence. What should you do?",
            sender = "WhatsApp Video Call (+91-9128374650)",
            options = listOf(
                "Transfer the money immediately so you aren't arrested",
                "Stay on video call for 24 hours as instructed",
                "Disconnect immediately, inform your family, and dial 1930 Cyber Helpline",
                "Plead with the officer to reduce the fine"
            ),
            correctIndex = 2,
            explanation = "Excellent! There is NO such legal concept as 'Digital Arrest' in India. Police, CBI, and ED never conduct arrests or demand money transfers over video calls.",
            badgeEarned = "👮 Cyber Savvy"
        ),
        QuizScenario(
            id = "quiz_5",
            category = "Banking",
            title = "Fake Bank Manager Asking CVV",
            scenarioText = "A polite caller claiming to be your SBI Branch Manager says your ATM debit card has expired and asks for the 16-digit card number, expiry date, and 3-digit CVV on the back to renew it. What will you do?",
            sender = "Caller: 'SBI Manager'",
            options = listOf(
                "Give the CVV because he is the bank manager",
                "Refuse and disconnect. Bank officials never ask for CVVs or card details over the phone",
                "Give the number but not the CVV",
                "Ask him to call tomorrow"
            ),
            correctIndex = 1,
            explanation = "Correct! No bank employee will EVER ask you for your ATM PIN, debit card CVV, or OTP under any circumstance. These details give direct access to your funds.",
            badgeEarned = "🏦 Vault Defender"
        ),
        QuizScenario(
            id = "quiz_6",
            category = "Tech",
            title = "AnyDesk / QuickSupport Request",
            scenarioText = "A customer care representative says your pension app has a glitch and asks you to install 'AnyDesk' from Play Store and read out the 9-digit number shown on the screen. What should you do?",
            sender = "Customer Care Agent",
            options = listOf(
                "Install it because it's available on Google Play Store",
                "Refuse and uninstall any screen-sharing app immediately",
                "Give the 9 digits but turn off Wi-Fi",
                "Install it on your tablet instead"
            ),
            correctIndex = 1,
            explanation = "Spot on! AnyDesk, RustDesk, and TeamViewer allow strangers to see your phone screen and take over your phone remotely. Scammers use it to read incoming OTPs and drain accounts.",
            badgeEarned = "📱 Device Sentinel"
        ),
        QuizScenario(
            id = "quiz_7",
            category = "Investment",
            title = "Guaranteed 40% Monthly Return Scheme",
            scenarioText = "A WhatsApp message advertises a 'Government-approved Senior Citizen Crypto Trust' that promises guaranteed 40% monthly interest returns with zero risk. What is the real situation?",
            sender = "Senior Wealth Club",
            options = listOf(
                "Invest a small amount of Rs 50,000 to see if returns come",
                "It is a fraudulent Ponzi scam. No legitimate financial scheme guarantees 40% monthly returns",
                "Ask them to send the government certificate on WhatsApp",
                "Recommend it to other retired friends"
            ),
            correctIndex = 1,
            explanation = "Correct! High guaranteed returns with 'zero risk' are 100% scams. In India, Senior Citizen Savings Scheme (SCSS) and RBI savings bonds offer around 7-8% annually. Anything offering 40% monthly is a scam.",
            badgeEarned = "💰 Wealth Protector"
        ),
        QuizScenario(
            id = "quiz_8",
            category = "Family",
            title = "Emergency Money from New Number",
            scenarioText = "You get a message: 'Dadu, I lost my phone while travelling. Send Rs 15,000 on this new UPI ID for hospital bills, please don't tell Mummy.' What is the safest next step?",
            sender = "New Unknown Number",
            options = listOf(
                "Send Rs 15,000 immediately out of love",
                "Call your grandchild's known old phone number and their parents before sending any money",
                "Reply asking how much more money they need",
                "Send Rs 5,000 just in case"
            ),
            correctIndex = 1,
            explanation = "Brilliant! Scammers exploit love and fear by pretending to be children or grandchildren in trouble. Always cross-verify with known family members first.",
            badgeEarned = "👨‍👩‍👧‍👦 Family Guardian"
        ),
        QuizScenario(
            id = "quiz_9",
            category = "Search",
            title = "Customer Care Number from Google Search",
            scenarioText = "Your LPG gas cylinder booking failed. You search Google for 'Bharat Gas customer care number' and call the first mobile number shown at the top. The person asks you to download a Rs 5 recharge app. What is happening?",
            sender = "Google Search Result",
            options = listOf(
                "It is authentic because it was at the top of Google",
                "Scammers place fake ads on Google with their own numbers. Only trust numbers on official bills or apps",
                "Pay the Rs 5 recharge",
                "Share your gas consumer ID and OTP"
            ),
            correctIndex = 1,
            explanation = "Correct! Cyber criminals often manipulate Google search results with fake customer care mobile numbers. Always find contact numbers directly from your official bill, passbook, or official app.",
            badgeEarned = "🔍 Smart Searcher"
        ),
        QuizScenario(
            id = "quiz_10",
            category = "Pension",
            title = "Pension Arrears Processing Fee",
            scenarioText = "A caller claims that Rs 65,000 in arrears under One Rank One Pension (OROP) or 8th Pay Commission is waiting for you, but you need to pay a Rs 1,500 file processing fee first. What should you do?",
            sender = "Caller: 'Pension Welfare Trust'",
            options = listOf(
                "Pay the Rs 1,500 fee to get Rs 65,000 arrears",
                "Hang up. Official pension revisions are credited directly with NO fees or deposits required",
                "Ask for a discount on the processing fee",
                "Give your bank login password instead"
            ),
            correctIndex = 1,
            explanation = "Exactly! Government pension departments never ask pensioners for processing fees or advance taxes. All pension arrears are automatically credited directly to your bank account.",
            badgeEarned = "🎖️ Veteran Safety Star"
        ),
        QuizScenario(
            id = "quiz_11",
            category = "Aadhaar",
            title = "Biometric Lock Protection",
            scenarioText = "How can senior citizens protect their Aadhaar from being misused for unauthorized biometrics or SIM cards without having to change their number?",
            sender = "UIDAI Awareness",
            options = listOf(
                "Post photos of Aadhaar card on Facebook to alert police",
                "Use the official mAadhaar app or UIDAI website to 'Lock Biometrics'",
                "Never use Aadhaar anywhere",
                "Laminate the Aadhaar card with plastic"
            ),
            correctIndex = 1,
            explanation = "Spot on! Locking your Aadhaar biometrics on the official mAadhaar app or myaadhaar.uidai.gov.in prevents anyone from misusing your fingerprints or iris scan for fraudulent transactions.",
            badgeEarned = "🔐 Aadhaar Champion"
        ),
        QuizScenario(
            id = "quiz_12",
            category = "Emergency",
            title = "Golden Hour Cyber Reporting",
            scenarioText = "God forbid if someone accidentally sends money to a scammer or reveals an OTP, what is the single most important helpline number to call within the first 1-2 hours in India?",
            sender = "National Cyber Crime Bureau",
            options = listOf(
                "Call 100 or 108",
                "Call 1930 — the National Cyber Financial Fraud Helpline — immediately",
                "Wait 24 hours to see if money comes back",
                "Post on Twitter/X only"
            ),
            correctIndex = 1,
            explanation = "Bingo! 1930 is India's dedicated helpline for reporting financial cyber fraud. Reporting within the first 2 hours ('The Golden Hour') gives police the highest chance to freeze the stolen money before scammers withdraw it.",
            badgeEarned = "🚨 Golden Hour Hero"
        )
    )

    val VOLUNTEERS: List<VolunteerProfile> = listOf(
        VolunteerProfile(
            id = "vol_1",
            name = "Ananya Desai",
            age = 22,
            location = "Bengaluru, Karnataka",
            languages = listOf("English", "Hindi", "Kannada"),
            bio = "Computer Science graduate passionate about helping senior citizens navigate smartphone apps safely without fear.",
            expertise = listOf("Jeevan Pramaan DLC", "UPI Safety", "WhatsApp Privacy"),
            verifiedBadge = true,
            rating = 4.9,
            totalSessions = 48,
            availabilityNote = "Weekdays 4:00 PM - 7:00 PM IST"
        ),
        VolunteerProfile(
            id = "vol_2",
            name = "Rohan Mehta",
            age = 24,
            location = "Ahmedabad, Gujarat",
            languages = listOf("English", "Gujarati", "Hindi"),
            bio = "Cybersecurity analyst volunteering on weekends to teach grandparents how to spot phishing SMS and fake pension calls.",
            expertise = listOf("Fake Bank Calls", "AnyDesk Prevention", "Online NetBanking"),
            verifiedBadge = true,
            rating = 5.0,
            totalSessions = 62,
            availabilityNote = "Saturdays & Sundays 10:00 AM - 1:00 PM IST"
        ),
        VolunteerProfile(
            id = "vol_3",
            name = "Karthik Subramanian",
            age = 23,
            location = "Chennai, Tamil Nadu",
            languages = listOf("English", "Tamil", "Telugu"),
            bio = "Engineering student who helped my grandfather recover from an OLX scam; now dedicated to teaching seniors digital defense.",
            expertise = listOf("OLX Scams", "QR Code Verification", "1930 Reporting"),
            verifiedBadge = true,
            rating = 4.8,
            totalSessions = 35,
            availabilityNote = "Evenings 5:30 PM - 8:30 PM IST"
        ),
        VolunteerProfile(
            id = "vol_4",
            name = "Sourav Banerjee",
            age = 25,
            location = "Kolkata, West Bengal",
            languages = listOf("English", "Bengali", "Hindi"),
            bio = "IT professional helping senior pensioners in Kolkata protect their pension savings and submit digital certificates safely.",
            expertise = listOf("Pension Portals (SPARSH)", "Electricity Bill Frauds", "Android Settings"),
            verifiedBadge = true,
            rating = 4.9,
            totalSessions = 51,
            availabilityNote = "Daily 6:00 PM - 9:00 PM IST"
        ),
        VolunteerProfile(
            id = "vol_5",
            name = "Tanvi Kulkarni",
            age = 21,
            location = "Pune, Maharashtra",
            languages = listOf("English", "Marathi", "Hindi"),
            bio = "UI/UX student conducting friendly interactive workshops for senior citizens on spotting fake WhatsApp messages.",
            expertise = listOf("Fake WhatsApp Forwards", "Google Pay Safety", "Aadhaar Biometric Lock"),
            verifiedBadge = true,
            rating = 5.0,
            totalSessions = 42,
            availabilityNote = "Weekends 2:00 PM - 6:00 PM IST"
        )
    )

    val SAFETY_TIPS: List<SafetyTip> = listOf(
        SafetyTip(
            id = "tip_1",
            title = "The Golden Rule of UPI",
            shortTip = "You NEVER enter your UPI PIN to receive money.",
            details = "Entering your UPI PIN authorises money to leave your bank account. If anyone asks you to enter your PIN to accept or claim money, it is a 100% scam.",
            tag = "UPI Safety"
        ),
        SafetyTip(
            id = "tip_2",
            title = "Jeevan Pramaan (Life Certificate)",
            shortTip = "Never click bit.ly or APK links for DLC submission.",
            details = "Digital Life Certificates can be submitted in person at any bank branch, head post office, through your doorstep postman, or via the official Jeevan Pramaan app.",
            tag = "Pension Safety"
        ),
        SafetyTip(
            id = "tip_3",
            title = "The Golden Hour Helpline: 1930",
            shortTip = "Report financial fraud within 2 hours to freeze stolen money.",
            details = "If you mistakenly transferred money to a fraudster or shared an OTP, dial 1930 immediately or visit cybercrime.gov.in. Early reporting allows police to freeze accounts.",
            tag = "Emergency Action"
        ),
        SafetyTip(
            id = "tip_4",
            title = "Screen Sharing Apps Warning",
            shortTip = "Never install AnyDesk or TeamViewer on a caller's request.",
            details = "Legitimate bank officers or pension staff will never ask you to install remote-desktop software or share a 9-digit code. These apps let strangers control your screen.",
            tag = "Phone Security"
        ),
        SafetyTip(
            id = "tip_5",
            title = "Beware of 'Digital Arrest' Calls",
            shortTip = "Police and CBI never arrest citizens over WhatsApp video calls.",
            details = "Scammers dress in fake police uniforms on video calls to intimidate seniors into transferring life savings. Disconnect immediately and call 1930.",
            tag = "Police Impersonation"
        )
    )

    val BANK_EMERGENCY_CONTACTS: List<BankEmergencyContact> = listOf(
        BankEmergencyContact(
            bankName = "State Bank of India (SBI)",
            tollFreeNumber = "1800 1234",
            cardBlockNumber = "1800 11 2211",
            upiBlockSms = "SMS BLOCK UPI to 9223008333",
            officialWebsite = "https://onlinesbi.sbi"
        ),
        BankEmergencyContact(
            bankName = "Punjab National Bank (PNB)",
            tollFreeNumber = "1800 180 2222",
            cardBlockNumber = "1800 103 2222",
            upiBlockSms = "SMS HOT to 5607040",
            officialWebsite = "https://pnbindia.in"
        ),
        BankEmergencyContact(
            bankName = "Bank of Baroda (BoB)",
            tollFreeNumber = "1800 5700",
            cardBlockNumber = "1800 258 4455",
            upiBlockSms = "SMS BLOCK to 8422009988",
            officialWebsite = "https://bankofbaroda.in"
        ),
        BankEmergencyContact(
            bankName = "HDFC Bank",
            tollFreeNumber = "1800 1600",
            cardBlockNumber = "1800 258 3838",
            upiBlockSms = "SMS BLOCK to 5676712",
            officialWebsite = "https://hdfcbank.com"
        ),
        BankEmergencyContact(
            bankName = "ICICI Bank",
            tollFreeNumber = "1800 1080",
            cardBlockNumber = "1800 2662",
            upiBlockSms = "SMS BLOCK to 9215676766",
            officialWebsite = "https://icicibank.com"
        ),
        BankEmergencyContact(
            bankName = "Canara Bank",
            tollFreeNumber = "1800 425 0018",
            cardBlockNumber = "1800 103 0018",
            upiBlockSms = "SMS CANBLOCK to 5607060",
            officialWebsite = "https://canarabank.com"
        )
    )
}
