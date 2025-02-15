package com.example.ecommerce;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.adapters.ItemAdapter;
import com.example.ecommerce.model.Item;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView itemRecyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private DatabaseReference databaseReference;
    private String selectedItemId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize Firebase Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("items");

        // Initialize UI Components
        itemRecyclerView = findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList, this::showUpdateDialog);
        itemRecyclerView.setAdapter(itemAdapter);

        Button addNewItemButton = findViewById(R.id.addNewItemButton);

        // Fetch items from Firebase
        loadItemsFromFirebase();

        // Open Dialog to Add New Item
        addNewItemButton.setOnClickListener(v -> showAddDialog());
    }

    private void loadItemsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item item = dataSnapshot.getValue(Item.class);
                    if (item != null) {
                        item.setId(dataSnapshot.getKey());
                        itemList.add(item);
                    }
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddDialog() {
        showItemDialog(this, "Add Item", null);
    }

    private void showUpdateDialog(Item item) {
        selectedItemId = item.getId();
        showItemDialog(this, "Update Item", item);
    }

//    private void showItemDialog(Context context, String title, Item item) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
//        builder.setView(view);
//
//        EditText itemNameInput = view.findViewById(R.id.dialogItemNameInput);
//        EditText itemPriceInput = view.findViewById(R.id.dialogItemPriceInput);
//        EditText itemImageInput = view.findViewById(R.id.dialogItemImageInput);
//        Button saveButton = view.findViewById(R.id.dialogSaveButton);
//        Button deleteButton = view.findViewById(R.id.dialogDeleteButton);
//
//        builder.setTitle(title);
//        AlertDialog dialog = builder.create();
//
//        if (item != null) {
//            itemNameInput.setText(item.getName());
//            itemPriceInput.setText(item.getPrice());
//            itemImageInput.setText(item.getImageUrl());
//            deleteButton.setVisibility(View.VISIBLE);
//        } else {
//            deleteButton.setVisibility(View.GONE);
//        }
//
//        saveButton.setOnClickListener(v -> {
//            String name = itemNameInput.getText().toString().trim();
//            String price = itemPriceInput.getText().toString().trim();
//            String imageUrl = itemImageInput.getText().toString().trim();
//
//            if (!name.isEmpty() && !price.isEmpty() && !imageUrl.isEmpty()) {
//                if (item == null) {
//                    // Add New Item
//                    String id = databaseReference.push().getKey();
//                    Item newItem = new Item(id, name, price, imageUrl);
//                    databaseReference.child(id).setValue(newItem);
//                    Toast.makeText(context, "Item Added!", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Update Item
//                    Map<String, Object> updates = new HashMap<>();
//                    updates.put("name", name);
//                    updates.put("price", price);
//                    updates.put("imageUrl", imageUrl);
//                    databaseReference.child(selectedItemId).updateChildren(updates);
//                    Toast.makeText(context, "Item Updated!", Toast.LENGTH_SHORT).show();
//                }
//                dialog.dismiss();
//            }
//        });
//
//        deleteButton.setOnClickListener(v -> {
//            if (item != null) {
//                databaseReference.child(selectedItemId).removeValue();
//                Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
private void showItemDialog(Context context, String title, Item item) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    View view = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
    builder.setView(view);

    EditText itemNameInput = view.findViewById(R.id.dialogItemNameInput);
    EditText itemPriceInput = view.findViewById(R.id.dialogItemPriceInput);
    EditText itemImageInput = view.findViewById(R.id.dialogItemImageInput);
    Spinner categorySpinner = view.findViewById(R.id.dialogCategorySpinner);
    Button saveButton = view.findViewById(R.id.dialogSaveButton);
    Button deleteButton = view.findViewById(R.id.dialogDeleteButton);

    builder.setTitle(title);
    AlertDialog dialog = builder.create();

    // Load categories into Spinner
    List<String> categoryNames = new ArrayList<>();
    DatabaseReference categoryReference = FirebaseDatabase.getInstance().getReference("categories");

    categoryReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            categoryNames.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                String categoryName = dataSnapshot.child("name").getValue(String.class);
                if (categoryName != null) {
                    categoryNames.add(categoryName);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, categoryNames);
            categorySpinner.setAdapter(adapter);

            // If editing an existing item, select its category
            if (item != null && item.getCategory() != null) {
                int position = categoryNames.indexOf(item.getCategory());
                if (position != -1) {
                    categorySpinner.setSelection(position);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(context, "Error loading categories", Toast.LENGTH_SHORT).show();
        }
    });

    if (item != null) {
        itemNameInput.setText(item.getName());
        itemPriceInput.setText(item.getPrice());
        itemImageInput.setText(item.getImageUrl());
        deleteButton.setVisibility(View.VISIBLE);
    } else {
        deleteButton.setVisibility(View.GONE);
    }

    saveButton.setOnClickListener(v -> {
        String name = itemNameInput.getText().toString().trim();
        String price = itemPriceInput.getText().toString().trim();
        String imageUrl = itemImageInput.getText().toString().trim();
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        if (!name.isEmpty() && !price.isEmpty() && !imageUrl.isEmpty() && selectedCategory != null) {
            if (item == null) {
                // Add New Item
                String id = databaseReference.push().getKey();
                Item newItem = new Item(id, name, price, imageUrl, selectedCategory);
                databaseReference.child(id).setValue(newItem);
                Toast.makeText(context, "Item Added!", Toast.LENGTH_SHORT).show();
            } else {
                // Update Item
                Map<String, Object> updates = new HashMap<>();
                updates.put("name", name);
                updates.put("price", price);
                updates.put("imageUrl", imageUrl);
                updates.put("category", selectedCategory);
                databaseReference.child(selectedItemId).updateChildren(updates);
                Toast.makeText(context, "Item Updated!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    });

    deleteButton.setOnClickListener(v -> {
        if (item != null) {
            databaseReference.child(selectedItemId).removeValue();
            Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    });

    dialog.show();
}

}