package edu.upc.projecte;

import java.io.Serializable;

public class Item implements Comparable<Item> {
    private String id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int imageResId;
    private int quantity; // Add this field

    public Item() {}

    public Item(String id, String name, String description, double price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity; // Initialize this field
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getQuantity() {
        return quantity; // Return the quantity
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Item other) {
        return this.name.compareTo(other.name);
    }
}