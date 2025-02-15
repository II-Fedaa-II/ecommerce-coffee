package com.example.ecommerce.model;

public class Item {
    private String id;  // Unique ID from Firebase
    private String name;
    private String imageUrl;
    private String price;
    private String category; // <-- Ensure this field exists
    // Default Constructor (Needed for Firebase)
    public Item() {}

    // Constructor with ID
    public Item(String id, String name, String price, String imageUrl, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

//package com.example.ecommerce.model;
//
//public class Item {
//    private String name;
//    private String imageUrl; // Firebase requires URL, not drawable resource ID
//    private String price;
//
//    // Default Constructor (Needed for Firebase)
//    public Item() {}
//
//    // Constructor
//    public Item(String name, String imageUrl, String price) {
//        this.name = name;
//        this.imageUrl = imageUrl;
//        this.price = price;
//    }
//
//    // Getters & Setters (Required for Firebase)
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//}


//package com.example.ecommerce.model;
//
//public class Item {
//    private final String name;
//    private final int imageResId;
//    private final String price;
//
//    public Item(String name, int imageResId, String price) {
//        this.name = name;
//        this.imageResId = imageResId;
//        this.price = price;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getImageResId() {
//        return imageResId;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//}
//
