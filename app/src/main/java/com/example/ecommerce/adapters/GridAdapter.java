package com.example.ecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.example.ecommerce.ItemActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Item;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private final Context context;
    private final List<Item> items;

    public GridAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            holder.imageView = convertView.findViewById(R.id.itemImage);
            holder.textView = convertView.findViewById(R.id.itemName);
            holder.priceView = convertView.findViewById(R.id.itemPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items.get(position);
//        Glide.with(context).load(item.getImageUrl()).into(holder.imageView);
        holder.textView.setText(item.getName());
        holder.priceView.setText(item.getPrice());

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("itemName", item.getName());
            intent.putExtra("itemPrice", item.getPrice());
            intent.putExtra("itemImage", item.getImageUrl());//getImageResId());
            intent.putExtra("itemDescription", "Sample description for " + item.getName());
            context.startActivity(intent);
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView priceView;
    }
}
