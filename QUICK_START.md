# LabourHub - Quick Start Guide (Hindi)

## 🚀 5 Minutes में APK बनाओ!

### Step 1: Prerequisites Install करो

```bash
# Flutter install karo
# Download: https://flutter.dev/docs/get-started/install

# Check installation
flutter doctor
```

### Step 2: Firebase Setup (Important!)

1. **Firebase Console में जाओ:** https://console.firebase.google.com/
2. **New Project बनाओ:** "LabourHub"
3. **Android App Add करो:**
   - Package Name: `com.labourhub.app`
   - Download `google-services.json`
   - `android/app/` folder में रखो

### Step 3: Code Setup

```bash
# Repository clone karo
git clone https://github.com/manojruidas411-collab/labour-management-app.git
cd labour-management-app

# Dependencies install karo
flutter pub get
```

### Step 4: Firebase Config Update करो

**File:** `lib/firebase_options.dart`

```dart
static const FirebaseOptions android = FirebaseOptions(
  apiKey: 'YOUR_API_KEY',  // Firebase console से copy karo
  appId: 'YOUR_APP_ID',
  messagingSenderId: 'YOUR_SENDER_ID',
  projectId: 'YOUR_PROJECT_ID',
  databaseURL: 'https://YOUR_PROJECT_ID.firebaseio.com',
  storageBucket: 'YOUR_PROJECT_ID.appspot.com',
);
```

### Step 5: APK Build करो

**Debug APK (जल्दी बनाने के लिए):**
```bash
flutter build apk --debug
```

Output: `build/app/outputs/flutter-apk/app-debug.apk`

**Release APK (Production के लिए):**

पहले Keystore बनाओ (एक बार ही):
```bash
keytool -genkey -v -keystore ~/upload-keystore.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias upload-key
```

फिर APK build करो:
```bash
flutter build apk --release
```

Output: `build/app/outputs/flutter-apk/app-release.apk`

### Step 6: Device में Install करो

**USB से:**
```bash
adb install -r build/app/outputs/flutter-apk/app-release.apk
```

**Android Studio से:**
1. Device/Emulator को connect करो
2. Menu: Build → Select Build APK(s)
3. APK file को select करके install करो

## 📱 App की तरह काम करेगा:

```
┌─────────────────────────────────┐
│  LabourHub                      │
├─────────────────────────────────┤
│  QR Scan करके Worker Add करो    │
│  ↓                              │
│  Workers की List देखो           │
│  ↓                              │
│  Factory Assign करो             │
│  ↓                              │
│  Attendance Mark करो            │
│  ↓                              │
│  Private Chat (Salary)          │
│  ↓                              │
│  Group Chat (Announcements)     │
│  ↓                              │
│  Payroll & Accounts (Money)     │
└─────────────────────────────────┘
```

## 🐛 Common Issues

### Issue 1: google-services.json not found
**Fix:** Firebase console से download करके `android/app/` में डालो

### Issue 2: Build failed
**Fix:**
```bash
flutter clean
flutter pub get
flutter build apk --release
```

### Issue 3: Gradle error
**Fix:**
```bash
flutter upgrade
flutter pub upgrade
```

## 📊 Final APK Info

| Type | Size | Time |
|------|------|------|
| Debug | 60-70 MB | 2-3 min |
| Release | 30-40 MB | 5-7 min |

## ✅ Testing करने के बाद

1. **Local Testing:** Device/Emulator में test करो
2. **Firebase Testing:** Test करते हुए Firestore rules check करो
3. **Share करो:** APK file दूसरों को भेज सकते हो

## 🎯 Next Steps

- [ ] Firebase Firestore Collections setup करो
- [ ] QR Code generation logic add करो
- [ ] Push Notifications setup करो
- [ ] Razorpay payment integration करो
- [ ] User authentication complete करो

## 📱 Device पर Run करने का सबसे आसान तरीका

```bash
# Device को USB से connect करो
# Terminal में आओ

flutter run

# अब app automatically device में खुल जाएगा!
```

---

**Happy Building! 🚀**

Questions? Check BUILD_INSTRUCTIONS.md for detailed guide
