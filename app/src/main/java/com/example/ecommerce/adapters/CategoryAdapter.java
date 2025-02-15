package com.example.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Category;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryName);
    }

    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getName());

        // Load category image using Glide
        if (category.getId().equals("all")) {
            holder.categoryImage.setImageResource(R.drawable.all_items); // Use a custom icon for "All"
        } else if (category.getImageUrl() != null && !category.getImageUrl().isEmpty()) {
            Glide.with(context).load(category.getImageUrl()).into(holder.categoryImage);
        } else {
            holder.categoryImage.setImageResource(R.drawable.arabica); // Use a default image if URL is missing
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}

//package com.example.ecommerce.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.ecommerce.R;
//import com.example.ecommerce.model.Category;
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//
//    private Context context;
//    private List<Category> categoryList;
//    private OnCategoryClickListener listener;
//
//    public interface OnCategoryClickListener {
//        void onCategoryClick(String categoryName);
//    }
//
//    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener) {
//        this.context = context;
//        this.categoryList = categoryList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.categories, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Category category = categoryList.get(position);
//        holder.categoryName.setText(category.getName());
//
//        // Click event for filtering items
//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onCategoryClick(category.getName());
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView categoryName;
//        ImageView categoryImage;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            categoryName = itemView.findViewById(R.id.categoryName);
//            categoryImage = itemView.findViewById(R.id.categoryImage);
//        }
//    }
//}

//package com.example.ecommerce.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.bumptech.glide.Glide;
//import com.example.ecommerce.R;
//import com.example.ecommerce.model.Category;
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
//
//    private Context context;
//    private List<Category> categoryList;
//    private OnCategoryClickListener onCategoryClickListener;
//
//    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener) {
//        this.context = context;
//        this.categoryList = categoryList;
//        this.onCategoryClickListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.categories, parent, false);
//        return new CategoryViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
//        Category category = categoryList.get(position);
//        holder.categoryName.setText(category.getName());
//
//        // Load Image from Firebase URL using Glide
//        Glide.with(context).load(category.getImageUrl()).into(holder.categoryImage);
//
//        holder.itemView.setOnClickListener(v -> {
//            onCategoryClickListener.onCategoryClick(category);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryList.size();
//    }
//
//    static class CategoryViewHolder extends RecyclerView.ViewHolder {
//        TextView categoryName;
//        ImageView categoryImage;
//
//        public CategoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//            categoryName = itemView.findViewById(R.id.categoryName);
//            categoryImage = itemView.findViewById(R.id.categoryImage);
//        }
//    }
//
//    public interface OnCategoryClickListener {
//        void onCategoryClick(Category category);
//    }
//}

//package com.example.ecommerce.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.ecommerce.R;
//import com.example.ecommerce.model.Category;
//
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
//
//    private Context context;
//    private List<Category> categoryList;
//    private OnCategoryClickListener onCategoryClickListener;
//
//    public CategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener) {
//        this.context = context;
//        this.categoryList = categoryList;
//        this.onCategoryClickListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.categories, parent, false);
//        return new CategoryViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
//        Category category = categoryList.get(position);
//        holder.categoryName.setText(category.getName());
//        holder.categoryImage.setImageResource(category.getImageResource());
//
//        holder.itemView.setOnClickListener(v -> {
//            onCategoryClickListener.onCategoryClick(category);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryList.size();
//    }
//
//    static class CategoryViewHolder extends RecyclerView.ViewHolder {
//        TextView categoryName;
//        ImageView categoryImage;
//
//        public CategoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//            categoryName = itemView.findViewById(R.id.categoryName);
//            categoryImage = itemView.findViewById(R.id.categoryImage);
//        }
//    }
//
//    public interface OnCategoryClickListener {
//        void onCategoryClick(Category category);
//    }
//}
