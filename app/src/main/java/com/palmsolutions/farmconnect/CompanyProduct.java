package com.palmsolutions.farmconnect;

public class CompanyProduct {
    String Image;
    String Title;
    String Description;
    String Price;

    public CompanyProduct(String image, String title, String description, String price) {
        Image = image;
        Title = title;
        Description = description;
        Price = price;
    }

    public CompanyProduct(){

    }
    public CompanyProduct(String title, String description, String price) {
        Title = title;
        Description = description;
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
