package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.adapters.GridAdapterFirebase;
import com.example.ecommerce.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private GridView gridView;
    private GridAdapterFirebase gridAdapter;
    private DatabaseReference databaseReference;
    private List<Item> favoriteItems;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(FavoriteActivity.this, LoginActivity.class));
            finish();
            return;
        }
        userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("favorite_items").child(userId);

        gridView = findViewById(R.id.myGridView);
        favoriteItems = new ArrayList<>();
        gridAdapter = new GridAdapterFirebase(this, databaseReference);

        gridView.setAdapter(gridAdapter);

        loadFavoritesFromFirebase();

        // Back Button Logic
        ImageView backButton = findViewById(R.id.ivBack);
        backButton.setOnClickListener(view -> finish()); // Close the activity on click
    }

    private void loadFavoritesFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                favoriteItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item item = dataSnapshot.getValue(Item.class);
                    if (item != null) {
                        favoriteItems.add(item);
                    }
                }
                gridAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(FavoriteActivity.this, "Error loading data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateToAcc(View view) {
        Intent home = new Intent(this, ProfileActivity.class);
        startActivity(home);
    }

    public void navigateToFavorites(View view) {
        Intent favorites = new Intent(this, FavoriteActivity.class);
        startActivity(favorites);
    }
}
