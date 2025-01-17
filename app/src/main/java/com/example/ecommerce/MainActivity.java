package com.example.ecommerce;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.WindowInsetsControllerCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.adapters.GridAdapter;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Item;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private List<Category> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        categoryList = Arrays.asList(
                new Category("Coffee", R.drawable.arabica),
                new Category("Tea", R.drawable.espresso),
                new Category("Pastries", R.drawable.coldbrew),
                new Category("Smoothies", R.drawable.coldbrew),
                new Category("Sandwiches", R.drawable.coldbrew),
                new Category("Juices", R.drawable.coldbrew),
                new Category("Cakes", R.drawable.coldbrew),
                new Category("Snacks", R.drawable.coldbrew)
        );

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList, category -> {
            Toast.makeText(MainActivity.this, "Selected: " + category.getName(), Toast.LENGTH_SHORT).show();
        });
        categoryRecyclerView.setAdapter(categoryAdapter);

        List<Item> items = Arrays.asList(
                new Item("Espresso", R.drawable.espresso2, "2.50"),
                new Item("Latte", R.drawable.latte, "3.50"),
                new Item("Green Tea", R.drawable.green_tea, "2.00"),
                new Item("Chocolate Cake", R.drawable.choco_cake, "4.00"),
                new Item("Smoothie", R.drawable.smoothie, "3.00"),
                new Item("Veggie Sandwich", R.drawable.veggie_sandwhich, "5.00"),
                new Item("Orange Juice", R.drawable.orange_juice, "2.50"),
                new Item("Chips", R.drawable.chips, "1.50")
        );

        GridView gridView = findViewById(R.id.myGridView);
        GridAdapter gridAdapter = new GridAdapter(this, items);
        gridView.setAdapter(gridAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
            insetsController.setAppearanceLightStatusBars(false);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void navigateToAcc(View view) {
        Intent home = new Intent(this, ProfileActivity.class);
        startActivity(home);
    }
}