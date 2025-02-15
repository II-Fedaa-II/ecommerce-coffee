package com.example.ecommerce;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.adapters.GridAdapterFirebase;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private DatabaseReference categoryReference, itemReference;
    private GridView gridView;
    private GridAdapterFirebase gridAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        categoryReference = FirebaseDatabase.getInstance().getReference("categories");
        itemReference = FirebaseDatabase.getInstance().getReference("items");

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categoryList = new ArrayList<>();
//        categoryAdapter = new CategoryAdapter(this, categoryList, category -> {
//            Log.d("CategoryClick", "Selected: " + category.getName());
//        });
        categoryAdapter = new CategoryAdapter(this, categoryList, category -> {
            Log.d("CategoryClick", "Selected: " + category);
            filterItemsByCategory(category);
        });

        categoryRecyclerView.setAdapter(categoryAdapter);

        loadCategoriesFromFirebase();

        gridView = findViewById(R.id.myGridView);
        gridAdapter = new GridAdapterFirebase(this, itemReference);
        gridView.setAdapter(gridAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
            insetsController.setAppearanceLightStatusBars(false);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

//    private void filterItemsByCategory(String category) {
//        itemReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Item> filteredItems = new ArrayList<>();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Item item = dataSnapshot.getValue(Item.class);
////                    if (item != null && item.getCategory().equals(category)) {
////                        filteredItems.add(item);
////                    }
//                    //  If "All" is selected, show all items
//                    if (item != null && (category.equals("All") || item.getCategory().equals(category))) {
//                        filteredItems.add(item);
//                    }
//                }
//                gridAdapter.updateData(filteredItems);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("FirebaseError", "Failed to filter items: " + error.getMessage());
//            }
//        });
//    }
private void filterItemsByCategory(String category) {
    itemReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<Item> filteredItems = new ArrayList<>();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                Item item = dataSnapshot.getValue(Item.class);

                if (item != null) {
                    item.setId(dataSnapshot.getKey()); // ✅ Ensure item ID is set

                    // If "All" is selected, show all items
                    if (category.equals("All") || item.getCategory().equals(category)) {
                        filteredItems.add(item);
                    }
                }
            }
            gridAdapter.updateData(filteredItems);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("FirebaseError", "Failed to filter items: " + error.getMessage());
        }
    });
}


    private void loadCategoriesFromFirebase() {
        categoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();

                categoryList.add(new Category("all", "All", null));

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    if (category != null) {
                        category.setId(dataSnapshot.getKey());
                        category.setImageUrl(dataSnapshot.child("imageUrl").getValue(String.class));
                        categoryList.add(category);
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load categories: " + error.getMessage());
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

    public void navigateToNotifications(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}




//package com.example.ecommerce;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.GridView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//
//import androidx.core.view.WindowInsetsControllerCompat;
//import androidx.viewpager2.widget.ViewPager2;
//
//import java.util.Arrays;
//import java.util.List;
//
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.ecommerce.adapters.CategoryAdapter;
//import com.example.ecommerce.adapters.GridAdapter;
//import com.example.ecommerce.model.Category;
//import com.example.ecommerce.model.Item;
//
//public class MainActivity extends AppCompatActivity {
//
//    private RecyclerView categoryRecyclerView;
//    private List<Category> categoryList;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        categoryList = Arrays.asList(
//                new Category("Coffee", R.drawable.arabica),
//                new Category("Tea", R.drawable.espresso),
//                new Category("Pastries", R.drawable.coldbrew),
//                new Category("Smoothies", R.drawable.coldbrew),
//                new Category("Sandwiches", R.drawable.coldbrew),
//                new Category("Juices", R.drawable.coldbrew),
//                new Category("Cakes", R.drawable.coldbrew),
//                new Category("Snacks", R.drawable.coldbrew)
//        );
//
//        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
//        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList, category -> {
//            Toast.makeText(MainActivity.this, "Selected: " + category.getName(), Toast.LENGTH_SHORT).show();
//        });
//        categoryRecyclerView.setAdapter(categoryAdapter);
//
//        List<Item> items = Arrays.asList(
//                new Item("Espresso", R.drawable.espresso2, "2.50"),
//                new Item("Latte", R.drawable.latte, "3.50"),
//                new Item("Green Tea", R.drawable.green_tea, "2.00"),
//                new Item("Chocolate Cake", R.drawable.choco_cake, "4.00"),
//                new Item("Smoothie", R.drawable.smoothie, "3.00"),
//                new Item("Veggie Sandwich", R.drawable.veggie_sandwhich, "5.00"),
//                new Item("Orange Juice", R.drawable.orange_juice, "2.50"),
//                new Item("Chips", R.drawable.chips, "1.50")
//        );
//
//        GridView gridView = findViewById(R.id.myGridView);
//        GridAdapter gridAdapter = new GridAdapter(this, items);
//        gridView.setAdapter(gridAdapter);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
//            insetsController.setAppearanceLightStatusBars(false);
//        } else {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//    }
//
//    public void navigateToAcc(View view) {
//        Intent home = new Intent(this, ProfileActivity.class);
//        startActivity(home);
//    }
//
//    public void navigateToFavorites(View view) {
//        Intent favorites = new Intent(this, FavoriteActivity.class);
//        startActivity(favorites);
//    }
//
//    public void navigateToNotifications(View view) {
//        Intent intent = new Intent(this, NotificationActivity.class);
//        startActivity(intent);
//    }
//
//}