package com.example.ecommerce;

import android.app.Application;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            // 🔥 Attempt Firebase Initialization
            FirebaseApp.initializeApp(this);

            if (FirebaseApp.getApps(this).isEmpty()) {
                Log.e("FirebaseError", "❌ Firebase is NOT initialized! Check google-services.json and dependencies.");
            } else {
                Log.d("FirebaseInit", "✅ Firebase initialized successfully.");
            }

        } catch (Exception e) {
            Log.e("FirebaseException", "🔥 Firebase initialization failed!", e);
        }

        // Debugging Google Services JSON
        try {
            FirebaseOptions options = FirebaseApp.getInstance().getOptions();
            Log.d("FirebaseDebug", "✅ Firebase Project ID: " + options.getProjectId());
            Log.d("FirebaseDebug", "✅ Firebase App Name: " + FirebaseApp.getInstance().getName());
            Log.d("FirebaseDebug", "✅ Firebase Database URL: " + options.getDatabaseUrl());
            Log.d("FirebaseDebug", "✅ Firebase Storage Bucket: " + options.getStorageBucket());
        } catch (Exception e) {
            Log.e("FirebaseDebug", "❌ Failed to fetch Firebase configuration. Possible misconfiguration in google-services.json.", e);
        }
    }
}
