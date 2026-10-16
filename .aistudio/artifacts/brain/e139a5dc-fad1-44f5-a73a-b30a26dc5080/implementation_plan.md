# SCAN SHIELD — Digital Safety Companion for Senior Pensioners

An AI-powered, senior-friendly digital safety companion designed to protect elderly pensioners in India from fake pension updates, fraudulent KYC alerts, UPI payment traps, impersonation scams, and cyber fraud with a cute, aesthetic, card-based interface and the empowering ethos: *"Pause. Check. Stay Safe. 💜"*

---

## User Review & Critical Decisions

> [!IMPORTANT]
> **Key Architecture & Aesthetic Alignment Choices**
>
> 1. **Visual Style & Aesthetic Direction**: Inspired by the uploaded Gen-Z / Pinterest card-stack design (`image.png`), Scan Shield features soft, high-contrast rounded cards in Lavender (`#B8A4FF`), Mint (`#BCEBD7`), Butter Yellow (`#FFE69A`), Peach (`#FFC6B0`), Warm Cream (`#FFF9F2`), and Dark Ink (`#292638`), accented by a custom purple shield mascot with sparkles and friendly vector sticker illustrations.
> 2. **Senior Accessibility First**: Every screen supports dynamic switching between **Normal**, **Large**, and **Extra Large** text scales, persisting in DataStore/Room. Touch targets exceed 48dp, and all primary buttons feature high-contrast badges and clear icon labels.
> 3. **Gemini Multimodal AI & Dual-Engine Scanner**: Seamlessly integrates direct Gemini REST API (`gemini-3.5-flash`) via `BuildConfig.GEMINI_API_KEY` for live multimodal screenshot analysis and conversational assistance, paired with a comprehensive offline rule-based heuristic engine with 16 distinct pre-configured Indian scam & benign test cases for instant verification without network delay.
> 4. **9 Indian Languages**: Full dynamic multilingual localization for **English, Hindi (हिंदी), Gujarati (ગુજરાતી), Tamil (தமிழ்), Bengali (বাংলা), Marathi (मराठी), Telugu (తెలుగు), Kannada (ಕನ್ನಡ), and Malayalam (മലയാളം)** with immediate UI re-rendering and voice TTS assistance.
> 5. **Privacy & Offline Integrity**: All trusted contacts, scan history, and quiz progress are strictly stored locally in an encrypted Room SQLite database. No personal credentials, OTPs, or photos are uploaded to third parties without explicit user consent.

---

## 1. Overview & Core Concept

- **What It Does**: Scan Shield acts as a 24/7 digital guardian for Indian senior citizens. It allows seniors or their family members to scan suspicious SMS messages, WhatsApp forwards, and screenshots (e.g. fake "Dear pensioner, submit Jeevan Pramaan immediately or pension stopped" notices, fake SBI/PNB APKs, electricity bill cut-off threats). It features the signature *"Pause Before You Pay 🛑"* interactive advisor before any UPI transaction, an instant one-tap SOS Emergency Centre connecting to the Indian National Cybercrime Helpline **1930** and trusted family, a voice assistant with text-to-speech, an interactive Learning Centre with 12 quiz scenarios, and a safe Volunteer Mentorship community.
- **Target Audience / Persona**: Elderly pensioners in India (ages 58–85+), retirees receiving EPFO/EPF/Defence/Central/State pensions, and their caring family members seeking peace of mind.
- **Key Value**: Eliminates fear and urgency tactics used by cyber fraudsters, provides instant clarity without technical jargon, and bridges the generational digital safety gap.

---

## 2. User Experience & Visual Design

### Key User Flows

1. **Welcoming Onboarding & Language Selection**:
   - Mascot greeting: *"Namaste! 💜 Welcome to Scan Shield."*
   - Immediate selection of preferred Indian language and comfortable text size (Normal / Large / Extra Large).
   - Setup of at least one Trusted Family Contact (with optional emergency phone dialling permissions).

2. **Home Dashboard & Safety Status**:
   - Prominent, glowing **SOS Emergency Button** in the top bar.
   - Dynamic Daily Safety Tip card (e.g., *"Banks never ask for your UPI PIN to credit your pension"*).
   - Four quick-action tactile cards:
     - 📸 **Scan a Screenshot** (Photo Picker or Gallery upload)
     - 💬 **Check Suspicious Message** (Quick paste or typed text / phone number)
     - 🛑 **Pause Before You Pay** (Step-by-step UPI & transaction check)
     - 🎙️ **Talk to Scan Shield** (Voice assistant)
   - Real-time personal safety summary (total scans, threats intercepted, lessons completed).
   - Recent scan history carousel with risk tags.

3. **Multimodal Scam Scanner & Evidence Breakdown**:
   - Users select a screenshot from gallery (via Android Photo Picker) or paste a message/link/phone number.
   - Test preset picker with **12 unique scam samples** (e.g. Jeevan Pramaan APK scam, SBI KYC account freeze, electricity bill power cut, fake lottery, part-time Telegram task, fake refund QR code) and **4 benign samples** (genuine EPFO pension credit SMS, official bank transaction alert, Aadhaar update receipt, gas subsidy notification).
   - Clear verdict banner: **High Risk 🚨**, **Suspicious ⚠️**, **No Obvious Warning Signs ℹ️**, or **Unable to Verify ❓**.
   - Bulleted breakdown of specific red flags (e.g., urgency language, suspicious `.apk` link, spoofed sender ID, request for remote app like AnyDesk/TeamViewer).
   - Actionable safe next steps and independent verification methods (e.g., official Sparsh pension portal, toll-free EPFO helpline).

4. **Pause Before You Pay 🛑**:
   - 5-step guided senior-friendly checklist before sending money on Google Pay / PhonePe / Paytm / BHIM:
     1. Do you know the recipient in real life?
     2. Why are they asking for money? (Urgency, hospital emergency, tax fee, courier parcel)
     3. Did they send you a QR code to "receive" money?
     4. Are they asking you to install AnyDesk, RustDesk, or TeamViewer?
     5. Can you call the person on a known verified number first?
   - Clear rule explained in bold: *"Remember: You NEVER enter your UPI PIN to receive money! Entering a PIN always deducts money from your account."*
   - Actionable outcome: **Stop and Verify 🛑**, **Proceed With Extreme Caution ⚠️**, or **Verified Safe Contact ✅**.

5. **Emergency Centre & SOS**:
   - One-touch dialling for India's Cyber Financial Fraud Helpline **1930**.
   - Direct link to official National Cyber Crime Reporting Portal (**https://cybercrime.gov.in/**).
   - Instant emergency call/SMS trigger to Trusted Family Contacts.
   - Bank-specific emergency freeze contact cards (SBI, PNB, Bank of Baroda, HDFC, ICICI, Canara Bank).
   - Step-by-step checklist: *"What to do within the Golden Hour of a scam"*.

6. **Voice Assistant & Audio Guidance**:
   - Friendly microphone button with animated ripple effect.
   - Speech recognition + Android Text-To-Speech (TTS) reading responses aloud in selected Indian languages.
   - Adjustable speech speed (Normal / Slow for senior clarity) and Repeat/Stop playback controls.

7. **Learning Centre & Quizzes**:
   - 12 realistic Indian scenario quizzes with multiple-choice questions, detailed explanations, and celebratory badges (e.g. *Pension Protector 🛡️*, *UPI Master 🏅*, *KYC Guardian ⭐*).
   - Offline digital safety guide bookmarked for reading anytime.

8. **Volunteer Community**:
   - Peer support connecting elderly learners with verified youth digital safety volunteers.
   - Clear volunteer profiles (language spoken, verified badge, safety score, available time slots).
   - Fictional, clearly labelled demo profiles with capability to request a 1-on-1 safety guidance session.
   - Strict zero-tolerance safety guardrails (volunteers can never ask for OTPs, PINs, or money).

---

### Visual Identity & Theme Tokens

- **Dominant Colors**:
  - Primary Lavender: `#B8A4FF` (soft, reassuring, accessible)
  - Deep Purple: `#5B45A6` (rich text, bold buttons, high contrast)
  - Mint Accent: `#BCEBD7` (safe states, calm confirmations)
  - Butter Yellow: `#FFE69A` (cautions, safety tips, highlighted pills)
  - Peach: `#FFC6B0` (badges, warm accents)
  - Warm Cream: `#FFF9F2` (comforting background, paper-like readability)
  - Dark Ink: `#292638` (maximum contrast typography)
  - Warning Rose: `#D95766` (high risk alerts, SOS emergency)
- **Typography & Scale**:
  - Friendly rounded headline typography with high legibility.
  - 3 dynamic scaling modes:
    - Normal (100% scale)
    - Large (120% scale)
    - Extra Large (140% scale, extra spacing for seniors)
- **Component Styling**:
  - 24dp rounded corners on primary cards with 1.5dp border outlines for depth.
  - Floating pill-style bottom navigation with high-contrast active indicator.
  - Minimum 56dp height on touch targets for jitter-free finger tapping.

---

## 3. Key Product Decisions & Trade-Offs

| Decision | Chosen Approach | Why & Trade-off |
| :--- | :--- | :--- |
| **Multimodal Scam Scanner** | Direct Gemini REST API (`gemini-3.5-flash`) + Embedded Expert Heuristics Engine | Gives seniors instant results even with weak connectivity, while leveraging full Gemini multimodal vision when online with an API key. Zero crash risk if offline. |
| **Local Data Storage** | Room Database (KSP) + DataStore Preferences | Ensures 100% local privacy of sensitive trusted contacts, scan history, and senior safety settings. Completely offline functional. |
| **Language Localization** | In-app dynamic locale dictionary for 9 Indian languages | Avoids requiring OS-level language switching which confuses elderly users; updates immediately across all screens with a single tap. |
| **Voice Interaction** | Native Android `TextToSpeech` + Speech Recognizer Intent with fallbacks | Works seamlessly without expensive cloud speech billing and provides zero-latency native accent rendering. |
| **Emergency Dialling** | Standard Android `Intent.ACTION_DIAL` | Complies with Play Store permissions policies without requesting dangerous background call permissions, while showing clear pre-filled numbers (1930 and family). |

---

## 4. Technical Architecture & Data Strategy

### System Architecture Diagram

```
┌────────────────────────────────────────────────────────────────────────┐
│                        SCAN SHIELD (UI Layer)                          │
├────────────────────────────────────────────────────────────────────────┤
│  MainActivity  ──►  ScanShieldApp (Scaffold & BottomNavigation)        │
│                                                                        │
│  ┌───────────────┬───────────────┬───────────────┬──────────────────┐  │
│  │  HomeScreen   │  ScannerView  │  PaymentCheck │  EmergencySOS    │  │
│  │  (Tips, SOS,  │  (Screenshot, │  (Pause Before│  (1930, Bank Hel-│  │
│  │   Quick Acts) │   SMS, Presets)│   You Pay)   │   plines, Family)│  │
│  ├───────────────┼───────────────┼───────────────┼──────────────────┤  │
│  │  VoiceAssist  │  LearnCentre  │  CommunityHub │  SettingsScreen  │  │
│  │  (TTS/Voice)  │  (12 Quizzes) │  (Volunteers) │  (Lang, TextSz)  │  │
│  └───────────────┴───────────────┴───────────────┴──────────────────┘  │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ State / Flow
┌───────────────────────────────────▼────────────────────────────────────┐
│              ScanShieldViewModel (StateFlow & MVI Actions)             │
│  - TextScale (Normal / Large / XL)    - Language (9 Indian Locales)    │
│  - ActiveScans & History               - Trusted Contacts & SOS State   │
│  - Voice Assistant State               - Quiz Progress & Badges         │
└──────────────┬────────────────────────────────────────────┬────────────┘
               │                                            │
┌──────────────▼──────────────┐             ┌───────────────▼────────────┐
│      Gemini / Scam Engine   │             │   Room Database & DataStore│
│  - Gemini REST Service      │             │  - ScanRecordDao           │
│    (gemini-3.5-flash)       │             │  - TrustedContactDao       │
│  - Heuristic Engine         │             │  - QuizProgressDao         │
│  - 16 Preset Samples        │             │  - UserPreferencesDataStore│
└─────────────────────────────┘             └────────────────────────────┘
```

### Data Models & Room Schema

1. **`ScanRecord`**:
   - `id`: Long (Auto-increment)
   - `timestamp`: Long
   - `type`: String (SCREENSHOT, SMS, UPI_LINK, PHONE_NUMBER)
   - `title`: String
   - `inputContent`: String (Message text or image URI)
   - `riskLevel`: String (HIGH_RISK, SUSPICIOUS, SAFE_VERIFIED, UNABLE_TO_VERIFY)
   - `summary`: String
   - `evidenceList`: List<String> (Stored as JSON / delimited string)
   - `recommendedActions`: List<String>
   - `isSaved`: Boolean

2. **`TrustedContact`**:
   - `id`: Long (Auto-increment)
   - `name`: String
   - `relationship`: String (Son, Daughter, Grandchild, Friend, Spouse)
   - `phoneNumber`: String
   - `isPrimary`: Boolean
   - `avatarColor`: String

3. **`QuizProgress`**:
   - `quizId`: String
   - `scenarioTitle`: String
   - `isCompleted`: Boolean
   - `score`: Int
   - `badgeEarned`: String?

4. **`AppSettings`**:
   - `language`: String (EN, HI, GU, TA, BN, MR, TE, KN, ML)
   - `textSize`: String (NORMAL, LARGE, EXTRA_LARGE)
   - `speechSpeed`: Float (0.75f, 1.0f)
   - `highContrast`: Boolean
   - `isDarkTheme`: Boolean

---

## 5. Verification & Testing Plan

1. **Build & Compilation**: Run `compile_applet` to ensure error-free compilation with Compose M3, Room KSP, OkHttp, and Kotlin 2.2.10.
2. **Dynamic Text Resizing**: Verify that switching between Normal, Large, and Extra Large instantly updates all typography, button heights, and card paddings without text clipping.
3. **Multilingual Rendering**: Verify translations and localized strings across all 9 languages on all primary screens.
4. **Scam Detection Scenarios**: Run all 12 scam test cases and 4 benign test cases to confirm accurate risk classification, clear evidence explanations, and emergency advisory notes.
5. **Photo Picker & Scanner Flow**: Verify image selection via modern Android `PickVisualMedia` contract with fallback to camera and manual text paste.
6. **SOS Dialler Integration**: Test dialler intent dispatch to 1930 and trusted contact numbers with pre-call confirmation dialog.
