package com.example.data

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    EN("en", "English", "English"),
    HI("hi", "Hindi", "हिंदी"),
    GU("gu", "Gujarati", "ગુજરાતી"),
    TA("ta", "Tamil", "தமிழ்"),
    BN("bn", "Bengali", "বাংলা"),
    MR("mr", "Marathi", "मराठी"),
    TE("te", "Telugu", "తెలుగు"),
    KN("kn", "Kannada", "ಕನ್ನಡ"),
    ML("ml", "Malayalam", "മലയാളം")
}

object Localization {

    private val translations: Map<String, Map<AppLanguage, String>> = mapOf(
        "app_name" to mapOf(
            AppLanguage.EN to "Scan Shield",
            AppLanguage.HI to "स्कैन शील्ड",
            AppLanguage.GU to "સ્કેન શીલ્ડ",
            AppLanguage.TA to "ஸ்கேன் ஷீல்ட்",
            AppLanguage.BN to "স্ক্যান শিল্ড",
            AppLanguage.MR to "स्कॅन शील्ड",
            AppLanguage.TE to "స్కాన్ షీల్డ్",
            AppLanguage.KN to "ಸ್ಕ್ಯಾನ್ ಶೀಲ್ಡ್",
            AppLanguage.ML to "സ്കാൻ ഷീൽഡ്"
        ),
        "tagline" to mapOf(
            AppLanguage.EN to "Pause. Check. Stay Safe. 💜",
            AppLanguage.HI to "रुकें। जांचें। सुरक्षित रहें। 💜",
            AppLanguage.GU to "થોભો. તપાસો. સુરક્ષિત રહો. 💜",
            AppLanguage.TA to "நிறுத்துங்கள். சரிபாருங்கள். பாதுகாப்பாக இருங்கள். 💜",
            AppLanguage.BN to "থামুন। যাচাই করুন। নিরাপদ থাকুন। 💜",
            AppLanguage.MR to "थांबा. तपासा. सुरक्षित राहा. 💜",
            AppLanguage.TE to "ఆగండి. తనిఖీ చేయండి. సురక్షితంగా ఉండండి. 💜",
            AppLanguage.KN to "ನಿಲ್ಲಿಸಿ. ಪರಿಶೀಲಿಸಿ. ಸುರಕ್ಷಿತವಾಗಿರಿ. 💜",
            AppLanguage.ML to "നിൽക്കൂ. പരിശോധിക്കൂ. സുരക്ഷിതമായിരിക്കൂ. 💜"
        ),
        "greeting" to mapOf(
            AppLanguage.EN to "Namaste! 💜 Welcome to Scan Shield.",
            AppLanguage.HI to "नमस्ते! 💜 स्कैन शील्ड में आपका स्वागत है।",
            AppLanguage.GU to "નમસ્તે! 💜 સ્કેન શીલ્ડમાં આપનું સ્વાગત છે.",
            AppLanguage.TA to "வணக்கம்! 💜 ஸ்கேன் ஷீல்டிற்கு நல்வரவு.",
            AppLanguage.BN to "নমস্কার! 💜 স্ক্যান শিল্ডে আপনাকে স্বাগতম।",
            AppLanguage.MR to "नमस्ते! 💜 स्कॅन शील्डमध्ये आपले स्वागत आहे.",
            AppLanguage.TE to "నమస్తే! 💜 స్కాన్ షీల్డ్‌కు స్వాగతం.",
            AppLanguage.KN to "ನಮಸ್ಕಾರ! 💜 ಸ್ಕ್ಯಾನ್ ಶೀಲ್ಡ್‌ಗೆ ಸುಸ್ವಾಗತ.",
            AppLanguage.ML to "നമസ്കാരം! 💜 സ്കാൻ ഷീൽഡിലേക്ക് സ്വാഗതം."
        ),
        "sos_button" to mapOf(
            AppLanguage.EN to "SOS Emergency",
            AppLanguage.HI to "आपातकालीन SOS",
            AppLanguage.GU to "ઇમરજન્સી SOS",
            AppLanguage.TA to "அவசர SOS",
            AppLanguage.BN to "জরুরি SOS",
            AppLanguage.MR to "तातडीचे SOS",
            AppLanguage.TE to "అత్యవసర SOS",
            AppLanguage.KN to "ತುರ್ತು SOS",
            AppLanguage.ML to "അടിയന്തര SOS"
        ),
        "nav_home" to mapOf(
            AppLanguage.EN to "Home",
            AppLanguage.HI to "होम",
            AppLanguage.GU to "હોમ",
            AppLanguage.TA to "முகப்பு",
            AppLanguage.BN to "হোম",
            AppLanguage.MR to "मुख्यपृष्ठ",
            AppLanguage.TE to "హోమ్",
            AppLanguage.KN to "ಮುಖಪುಟ",
            AppLanguage.ML to "ഹോം"
        ),
        "nav_scan" to mapOf(
            AppLanguage.EN to "Scan",
            AppLanguage.HI to "स्कैन",
            AppLanguage.GU to "સ્કેન",
            AppLanguage.TA to "ஸ்கேன்",
            AppLanguage.BN to "স্ক্যান",
            AppLanguage.MR to "स्कॅन",
            AppLanguage.TE to "స్కాన్",
            AppLanguage.KN to "ಸ್ಕ್ಯಾನ್",
            AppLanguage.ML to "സ്കാൻ"
        ),
        "nav_learn" to mapOf(
            AppLanguage.EN to "Learn",
            AppLanguage.HI to "सीखें",
            AppLanguage.GU to "શીખો",
            AppLanguage.TA to "கற்க",
            AppLanguage.BN to "শিখুন",
            AppLanguage.MR to "शिका",
            AppLanguage.TE to "నేర్చుకోండి",
            AppLanguage.KN to "ಕಲಿಯಿರಿ",
            AppLanguage.ML to "പഠിക്കുക"
        ),
        "nav_community" to mapOf(
            AppLanguage.EN to "Community",
            AppLanguage.HI to "समुदाय",
            AppLanguage.GU to "સમુદાય",
            AppLanguage.TA to "சமூகம்",
            AppLanguage.BN to "কমিউনিটি",
            AppLanguage.MR to "समुदाय",
            AppLanguage.TE to "కమ్యూనిటీ",
            AppLanguage.KN to "ಸಮುದಾಯ",
            AppLanguage.ML to "കമ്മ്യൂണിറ്റി"
        ),
        "nav_profile" to mapOf(
            AppLanguage.EN to "Settings",
            AppLanguage.HI to "सेटिंग्स",
            AppLanguage.GU to "સેટિંગ્સ",
            AppLanguage.TA to "அமைப்புகள்",
            AppLanguage.BN to "সেটিংস",
            AppLanguage.MR to "सेटिंग्ज",
            AppLanguage.TE to "సెట్టింగ్‌లు",
            AppLanguage.KN to "ಸೆಟ್ಟಿಂಗ್ಸ್",
            AppLanguage.ML to "സെറ്റിംഗ്സ്"
        ),
        "action_scan_screenshot" to mapOf(
            AppLanguage.EN to "Scan a Screenshot",
            AppLanguage.HI to "स्क्रीनशॉट स्कैन करें",
            AppLanguage.GU to "સ્ક્રીનશોટ સ્કેન કરો",
            AppLanguage.TA to "ஸ்கிரீன்ஷாட்டை ஸ்கேன் செய்க",
            AppLanguage.BN to "স্ক্রিনশট স্ক্যান করুন",
            AppLanguage.MR to "स्क्रीनशॉट स्कॅन करा",
            AppLanguage.TE to "స్క్రీన్‌షాట్‌ను స్కాన్ చేయండి",
            AppLanguage.KN to "ಸ್ಕ್ರೀನ್‌ಶಾಟ್ ಸ್ಕ್ಯಾನ್ ಮಾಡಿ",
            AppLanguage.ML to "സ്ക്രീൻഷോട്ട് സ്കാൻ ചെയ്യുക"
        ),
        "action_check_message" to mapOf(
            AppLanguage.EN to "Check Message",
            AppLanguage.HI to "संदेश की जांच करें",
            AppLanguage.GU to "મેસેજ તપાસો",
            AppLanguage.TA to "செய்தியை சோதிக்க",
            AppLanguage.BN to "মেসেজ যাচাই করুন",
            AppLanguage.MR to "संदेश तपासा",
            AppLanguage.TE to "సందేశాన్ని తనిఖీ చేయండి",
            AppLanguage.KN to "ಸಂದೇಶವನ್ನು ಪರಿಶೀಲಿಸಿ",
            AppLanguage.ML to "സന്ദേശം പരിശോധിക്കുക"
        ),
        "action_pause_pay" to mapOf(
            AppLanguage.EN to "Pause Before You Pay",
            AppLanguage.HI to "भुगतान से पहले रुकें",
            AppLanguage.GU to "ચુકવણી કરતા પહેલા થોભો",
            AppLanguage.TA to "பணம் செலுத்தும் முன் நில்லுங்கள்",
            AppLanguage.BN to "পেমেন্টের আগে থামুন",
            AppLanguage.MR to "पैसे देण्यापूर्वी थांबा",
            AppLanguage.TE to "చెల్లించే ముందు ఆగండి",
            AppLanguage.KN to "ಪಾವತಿಸುವ ಮೊದಲು ನಿಲ್ಲಿ",
            AppLanguage.ML to "പണം നൽകുന്നതിന് മുൻപ് നിൽക്കൂ"
        ),
        "action_talk_shield" to mapOf(
            AppLanguage.EN to "Talk to Scan Shield",
            AppLanguage.HI to "स्कैन शील्ड से बात करें",
            AppLanguage.GU to "સ્કેન શીલ્ડ સાથે વાત કરો",
            AppLanguage.TA to "ஸ்கேன் ஷீல்டுடன் பேசுங்கள்",
            AppLanguage.BN to "স্ক্যান শিল্ডের সাথে কথা বলুন",
            AppLanguage.MR to "स्कॅन शील्डशी बोला",
            AppLanguage.TE to "స్కాన్ షీల్డ్‌తో మాట్లాడండి",
            AppLanguage.KN to "ಸ್ಕ್ಯಾನ್ ಶೀಲ್ಡ್ ಜತೆ ಮಾತನಾಡಿ",
            AppLanguage.ML to "സ്കാൻ ഷീൽഡുമായി സംസാരിക്കൂ"
        ),
        "pause_heading" to mapOf(
            AppLanguage.EN to "Wait! Let's check before you pay. 🛑",
            AppLanguage.HI to "रुकिए! भुगतान करने से पहले जांच लें। 🛑",
            AppLanguage.GU to "ઊભા રહો! ચૂકવણી કરતા પહેલા તપાસીએ. 🛑",
            AppLanguage.TA to "பொறுங்கள்! பணம் செலுத்துவதற்கு முன் சரிபாருங்கள். 🛑",
            AppLanguage.BN to "একটু দাঁড়ান! পেমেন্ট করার আগে যাচাই করে নিই। 🛑",
            AppLanguage.MR to "थांबा! पैसे भरण्यापूर्वी तपासून पाहूया. 🛑",
            AppLanguage.TE to "ఆగండి! మీరు చెల్లించే ముందు తనిఖీ చేద్దాం. 🛑",
            AppLanguage.KN to "ತಡೆಯಿರಿ! ನೀವು ಪಾವತಿಸುವ ಮೊದಲು ಪರಿಶೀಲಿಸೋಣ. 🛑",
            AppLanguage.ML to "നിൽക്കൂ! പണം നൽകുന്നതിന് മുൻപ് നമുക്ക് പരിശോധിക്കാം. 🛑"
        ),
        "daily_safety_tip" to mapOf(
            AppLanguage.EN to "Daily Safety Tip",
            AppLanguage.HI to "आज की सुरक्षा सलाह",
            AppLanguage.GU to "દૈનિક સુરક્ષા સલાહ",
            AppLanguage.TA to "தினசரி பாதுகாப்பு குறிப்பு",
            AppLanguage.BN to "দৈনিক নিরাপত্তা পরামর্শ",
            AppLanguage.MR to "दैनिक सुरक्षा सल्ला",
            AppLanguage.TE to "రోజువారీ భద్రతా చిట్కా",
            AppLanguage.KN to "ದೈನಂದಿನ ಸುರಕ್ಷತಾ ಸಲಹೆ",
            AppLanguage.ML to "ദിവസേനയുള്ള സുരക്ഷാ ടിപ്പ്"
        ),
        "trusted_family" to mapOf(
            AppLanguage.EN to "Trusted Family Contact",
            AppLanguage.HI to "विश्वसनीय पारिवारिक संपर्क",
            AppLanguage.GU to "વિશ્વસનીય પારિવારિક સંપર્ક",
            AppLanguage.TA to "நம்பகமான குடும்பத் தொடர்பு",
            AppLanguage.BN to "বিশ্বস্ত পারিবারিক যোগাযোগ",
            AppLanguage.MR to "विश्वासू कौटुंबिक संपर्क",
            AppLanguage.TE to "విశ్వసనీయ కుటుంబ పరిచయం",
            AppLanguage.KN to "ವಿಶ್ವಾಸಾರ್ಹ ಕುಟುಂಬ ಸಂಪರ್ಕ",
            AppLanguage.ML to "വിശ്വസ്ത കുടുംബ കോൺടാക്റ്റ്"
        ),
        "recent_scans" to mapOf(
            AppLanguage.EN to "Recent Scam Checks",
            AppLanguage.HI to "हाल की जांचें",
            AppLanguage.GU to "તાજેતરની તપાસ",
            AppLanguage.TA to "சமீபத்திய சோதனைகள்",
            AppLanguage.BN to "সাম্প্রতিক যাচাই",
            AppLanguage.MR to "नुकत्याच केलेल्या तपासण्या",
            AppLanguage.TE to "ఇటీవలి స్కాన్ తనిఖీలు",
            AppLanguage.KN to "ಇತ್ತೀಚಿನ ತನಿಖೆಗಳು",
            AppLanguage.ML to "സമീപകാല പരിശോധനകൾ"
        ),
        "call_1930" to mapOf(
            AppLanguage.EN to "Call Cyber Helpline 1930",
            AppLanguage.HI to "साइबर हेल्पलाइन 1930 पर कॉल करें",
            AppLanguage.GU to "સાયબર હેલ્પલાઇન 1930 પર કૉલ કરો",
            AppLanguage.TA to "சைபர் உதவி எண் 1930-ஐ அழைக்கவும்",
            AppLanguage.BN to "সাইবার হেল্পলাইন 1930 এ কল করুন",
            AppLanguage.MR to "सायबर हेल्पलाइन 1930 वर कॉल करा",
            AppLanguage.TE to "సైబర్ హెల్ప్‌లైన్ 1930 కి కాల్ చేయండి",
            AppLanguage.KN to "ಸೈಬರ್ ಸಹಾಯವಾಣಿ 1930 ಕರೆ ಮಾಡಿ",
            AppLanguage.ML to "സൈബർ ഹെൽപ്പ് ലൈൻ 1930 വിളിക്കുക"
        ),
        "upi_pin_rule" to mapOf(
            AppLanguage.EN to "CRITICAL: You NEVER enter your UPI PIN to receive money! Entering a PIN authorises payment.",
            AppLanguage.HI to "महत्वपूर्ण: पैसे प्राप्त करने के लिए कभी भी UPI पिन दर्ज न करें! पिन दर्ज करने से पैसे कटते हैं।",
            AppLanguage.GU to "ખાસ નોંધ: પૈસા મેળવવા માટે ક્યારેય UPI પિન નાખવો પડતો નથી! પિન નાખવાથી પૈસા કપાય છે.",
            AppLanguage.TA to "முக்கியம்: பணம் பெற ஒருபோதும் UPI பின்னை உள்ளிட வேண்டாம்! பின்னை உள்ளிட்டால் பணம் கழியும்.",
            AppLanguage.BN to "জরুরি: টাকা পাওয়ার জন্য কখনোই UPI পিন দেবেন না! পিন দিলে আপনার অ্যাকাউন্ট থেকে টাকা কেটে যাবে।",
            AppLanguage.MR to "महत्त्वाचे: पैसे मिळवण्यासाठी कधीही UPI पिन टाकू नका! पिन टाकल्याने पैसे कट होतात.",
            AppLanguage.TE to "ముఖ్యం: డబ్బు పొందడానికి ఎప్పుడూ UPI పిన్ నమోదు చేయవద్దు! పిన్ ఎంటర్ చేస్తే డబ్బు కట్ అవుతుంది.",
            AppLanguage.KN to "ಮುಖ್ಯ: ಹಣ ಪಡೆಯಲು ಎಂದಿಗೂ UPI ಪಿನ್ ನಮೂದಿಸಬೇಡಿ! ಪಿನ್ ನಮೂದಿಸಿದರೆ ಹಣ ಖಾತೆಯಿಂದ ಕಡಿತಗೊಳ್ಳುತ್ತದೆ.",
            AppLanguage.ML to "പ്രധാനം: പണം ലഭിക്കുന്നതിനായി ഒരിക്കലും UPI പിൻ നൽകരുത്! പിൻ അടിച്ചാൽ പണം നഷ്ടപ്പെടും."
        )
    )

    fun get(key: String, lang: AppLanguage): String {
        val map = translations[key] ?: return key
        return map[lang] ?: map[AppLanguage.EN] ?: key
    }
}
