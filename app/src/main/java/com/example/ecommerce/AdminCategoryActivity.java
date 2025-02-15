package com.example.ecommerce;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerce.adapters.CategoryAdapterAdmin;
import com.example.ecommerce.model.Category;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView;
    private CategoryAdapterAdmin categoryAdapter;
    private List<Category> categoryList;
    private DatabaseReference categoryReference;
    private String selectedCategoryId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        // Initialize Firebase Reference
        categoryReference = FirebaseDatabase.getInstance().getReference("categories");

        // Initialize UI Components
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapterAdmin(categoryList, this::showUpdateDialog);
        categoryRecyclerView.setAdapter(categoryAdapter);

        Button addNewCategoryButton = findViewById(R.id.addNewCategoryButton);

        // Fetch categories from Firebase
        loadCategoriesFromFirebase();

        // Open Dialog to Add New Category
        addNewCategoryButton.setOnClickListener(v -> showAddDialog());
    }

    private void loadCategoriesFromFirebase() {
        categoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    if (category != null) {
                        category.setId(dataSnapshot.getKey());
                        categoryList.add(category);
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminCategoryActivity.this, "Error loading categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddDialog() {
        showCategoryDialog(this, "Add Category", null);
    }

    private void showUpdateDialog(Category category) {
        selectedCategoryId = category.getId();
        showCategoryDialog(this, "Update Category", category);
    }

    private void showCategoryDialog(Context context, String title, Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_category, null);
        builder.setView(view);

        EditText categoryNameInput = view.findViewById(R.id.dialogCategoryNameInput);
        EditText categoryImageInput = view.findViewById(R.id.dialogCategoryImageInput); // Image URL input
        Button saveButton = view.findViewById(R.id.dialogSaveButton);
        Button deleteButton = view.findViewById(R.id.dialogDeleteButton);

        builder.setTitle(title);
        AlertDialog dialog = builder.create();

        if (category != null) {
            categoryNameInput.setText(category.getName());
            categoryImageInput.setText(category.getImageUrl()); // Set image URL
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.GONE);
        }

        saveButton.setOnClickListener(v -> {
            String name = categoryNameInput.getText().toString().trim();
            String imageUrl = categoryImageInput.getText().toString().trim();

            if (!name.isEmpty() && !imageUrl.isEmpty()) {
                if (category == null) {
                    // Add New Category
                    String id = categoryReference.push().getKey();
                    Category newCategory = new Category(id, name, imageUrl);
                    categoryReference.child(id).setValue(newCategory);
                    Toast.makeText(context, "Category Added!", Toast.LENGTH_SHORT).show();
                } else {
                    // Update Category
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("name", name);
                    updates.put("imageUrl", imageUrl);
                    categoryReference.child(selectedCategoryId).updateChildren(updates);
                    Toast.makeText(context, "Category Updated!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (category != null) {
                categoryReference.child(selectedCategoryId).removeValue();
                Toast.makeText(context, "Category Deleted!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
