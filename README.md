# SpendWise v3.0 — Feature Update

## New Features in This Version
- ✅ Registration: First name, last name, gender, email + optional profile photo upload
- ✅ Header: User's name and profile photo shown top-right on every screen after login
- ✅ Net Balance card: White background, black text
- ✅ Transaction icon: Updated to double-arrow (income/expense) icon
- ✅ Expense category icon: Changed from cup to 💳
- ✅ Profile page: Displays full account info (name, gender, email, photo)
- ✅ Add Expense: Optional receipt/photo upload per expense entry (shown inline in list)

## Setup Before Running

### Step 1 — Firebase Storage Rules
In Firebase Console → Storage → Rules, paste:
```
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /profiles/{userId}/{allPaths=**} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    match /receipts/{userId}/{allPaths=**} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```
Click Publish.

### Step 2 — Enable Firebase Storage
Firebase Console → Build → Storage → Get started → Start in test mode → Done

### Step 3 — Replace google-services.json
Download real google-services.json from Firebase Console → Project Settings → Your Apps
Replace app/google-services.json with the downloaded file.

### Step 4 — Add Web Client ID
Firebase Console → Authentication → Sign-in method → Google → copy Web Client ID
Open app/src/main/res/values/strings.xml
Replace YOUR_WEB_CLIENT_ID_HERE with your actual ID

### Step 5 — Open in Android Studio
File → Open → select SpendWise folder → wait for Gradle sync → Run ▶

## Requirements
- Android Studio Hedgehog or newer
- JDK 17 or 21
- minSdk 26 (Android 8.0+)
