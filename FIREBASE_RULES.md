# ⚠️ IMPORTANT — Copy these rules into Firebase Console

## Firestore Rules
Go to: Firebase Console → Firestore → Rules tab → paste this → Publish

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
      match /{subcollection}/{docId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
      }
    }
  }
}
```

## Firebase Storage Rules
Go to: Firebase Console → Storage → Rules tab → paste this → Publish

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

These rules fix:
- "Object does not exist at location" when saving transactions
- "PERMISSION_DENIED: Missing or insufficient permissions" when loading transactions
