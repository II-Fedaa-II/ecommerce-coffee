package com.example.ecommerce.model;

public class Category {
    private String id;
    private String name;
    private String imageUrl; // Store image as a URL

    public Category() {
        // Default constructor required for Firebase
    }

    public Category(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // Getter and Setter for ID
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

//package com.example.ecommerce.model;
//
//public class Category {
//    private String name;
//    private int imageResource;
//
//    public Category(String name, int imageResource) {
//        this.name = name;
//        this.imageResource = imageResource;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getImageResource() {
//        return imageResource;
//    }
//
//    public void setImageResource(int imageResource) {
//        this.imageResource = imageResource;
//    }
//}
