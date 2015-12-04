package org.progos.tasteofabuddhabicms.model;

/**
 * Created by NomBhatti on 11/30/2015.
 */
public class RestaurantItem {

    String title;
    String category;
    String description;
    String price;

    public RestaurantItem(String title, String category, String description, String price) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
