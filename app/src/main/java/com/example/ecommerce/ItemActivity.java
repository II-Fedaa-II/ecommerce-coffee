package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ItemActivity extends AppCompatActivity {
TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.item_activity);

        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productDescription = findViewById(R.id.productDescription);

        String name = getIntent().getStringExtra("itemName");
        String price = getIntent().getStringExtra("itemPrice");
        int imageResId = getIntent().getIntExtra("itemImage", R.drawable.profile_logo);
        String description = getIntent().getStringExtra("itemDescription");

        productImage.setImageResource(imageResId);
        productName.setText(name);
        productPrice.setText(price);
        productDescription.setText(description);
         }
}