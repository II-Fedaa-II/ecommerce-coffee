package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ItemActivity extends AppCompatActivity {
    private Button addToFavoritesButton;
    private DatabaseReference favoritesRef;
    private String userId;
    private String itemId;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.item_activity);

        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productDescription = findViewById(R.id.productDescription);
        Button addToCartButton = findViewById(R.id.addToCartButton);
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);

        Intent intent = getIntent();
        String name = intent.getStringExtra("itemName");
        String price = intent.getStringExtra("itemPrice");
        String imageUrl = intent.getStringExtra("itemImage");
        itemId = intent.getStringExtra("itemId");
        String description = intent.getStringExtra("itemDescription");

        if (itemId == null || itemId.isEmpty()) {
            Toast.makeText(this, "Error: Item ID is missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        productName.setText(name);
        productPrice.setText("$" + price);
        productDescription.setText(description);

        Glide.with(this)
                .load(imageUrl)
                .into(productImage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
            favoritesRef = FirebaseDatabase.getInstance().getReference("favorite_items").child(userId);
            checkIfFavorite();
        }

        addToCartButton.setOnClickListener(v -> {
            // Implement add-to-cart functionality
        });

        addToFavoritesButton.setOnClickListener(v -> toggleFavorite(name, price, imageUrl));

        // Back Button Logic
        ImageView backButton = findViewById(R.id.ivBack);
        backButton.setOnClickListener(view -> finish()); // Close the activity on click
    }

    private void checkIfFavorite() {
        if (itemId == null || itemId.isEmpty()) {
            return;
        }

        favoritesRef.child(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    isFavorite = true;
                    addToFavoritesButton.setText("Remove from Favorites");
                } else {
                    isFavorite = false;
                    addToFavoritesButton.setText("Add to Favorites");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ItemActivity.this, "Error checking favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleFavorite(String name, String price, String imageUrl) {
        if (itemId == null || itemId.isEmpty()) {
            Toast.makeText(this, "Error: Invalid item ID", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isFavorite) {
            favoritesRef.child(itemId).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ItemActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                    addToFavoritesButton.setText("Add to Favorites");
                }
            });
        } else {
            Map<String, Object> favoriteItem = new HashMap<>();
            favoriteItem.put("name", name);
            favoriteItem.put("price", price);
            favoriteItem.put("imageUrl", imageUrl);

            favoritesRef.child(itemId).setValue(favoriteItem).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ItemActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    isFavorite = true;
                    addToFavoritesButton.setText("Remove from Favorites");
                }
            });
        }
    }
}