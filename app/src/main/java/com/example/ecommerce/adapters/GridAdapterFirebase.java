package com.example.ecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import com.bumptech.glide.Glide;
import com.example.ecommerce.ItemActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Item;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class GridAdapterFirebase extends BaseAdapter {

    private Context context;
    private List<Item> itemList;
    private DatabaseReference databaseReference;

    public GridAdapterFirebase(Context context, DatabaseReference databaseReference) {
        this.context = context;
        this.databaseReference = databaseReference;
        this.itemList = new ArrayList<>();

        // Load data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item item = dataSnapshot.getValue(Item.class);
                    if (item != null) {
                        item.setId(dataSnapshot.getKey()); // Ensure item ID is stored
                        itemList.add(item);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error loading data: " + error.getMessage());
            }
        });
    }

    public void updateData(List<Item> newItems) {
        this.itemList = newItems;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.itemImage);
            holder.name = convertView.findViewById(R.id.itemName);
            holder.price = convertView.findViewById(R.id.itemPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind data
        Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("$" + item.getPrice());
        Glide.with(context).load(item.getImageUrl()).into(holder.image);

        // Set click listener to navigate to ItemActivity
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("itemId", item.getId()); // Ensure ID is passed
            intent.putExtra("itemName", item.getName());
            intent.putExtra("itemPrice", item.getPrice());
            intent.putExtra("itemImage", item.getImageUrl() != null ? item.getImageUrl() : "");
            intent.putExtra("itemDescription", "Sample description for " + item.getName());

            if (context instanceof android.app.Activity) {
                context.startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView name, price;
    }
}
