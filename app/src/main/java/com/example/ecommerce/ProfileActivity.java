package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView profileName, profileEmail;
    private Button logoutButton, adminPanelButton, adminCategoryPanelButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI Components
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        logoutButton = findViewById(R.id.logoutButton);
        adminPanelButton = findViewById(R.id.adminPanelButton);
        adminCategoryPanelButton = findViewById(R.id.adminCategoryPanelButton);
        adminCategoryPanelButton.setVisibility(View.GONE);
        adminPanelButton.setVisibility(View.GONE); // Hide by default

        // Get Current User
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            profileEmail.setText(user.getEmail());

            // Check User Role
            databaseReference.child(user.getUid()).child("role").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists() && snapshot.getValue(String.class).equals("admin")) {
                        adminPanelButton.setVisibility(View.VISIBLE); // Show Admin Panel Button
                        adminCategoryPanelButton.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, "Error checking role", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Logout Button Click Event
        logoutButton.setOnClickListener(v -> logoutUser());

        // Admin Panel Button Click Event
        adminPanelButton.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, AdminActivity.class));
        });

        adminCategoryPanelButton.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, AdminCategoryActivity.class));
        });

        // Back Button Logic
        ImageView backButton = findViewById(R.id.ivBack);
        backButton.setOnClickListener(view -> finish()); // Close the activity on click
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(ProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }
}
