package com.palmsolutions.farmconnect;

import java.util.HashMap;
import java.util.Map;

public class CompanyProduct {
    String Image;
    String Title;
    String Description;
    String Price;
    String Product_id;
    String Product_type;
    String user_uuid;

    public CompanyProduct(String image, String title, String description, String price, String product_id, String product_type, String user_uuid) {
        Image = image;
        Title = title;
        Description = description;
        Price = price;
        Product_id = product_id;
        this.Product_type = product_type;
        this.user_uuid = user_uuid;
    }



    public CompanyProduct(){

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("Image", Image);
        productMap.put("Title", Title);
        productMap.put("Description", Description);
        productMap.put("Price", Price);
        productMap.put("Product_id", Product_id);
        productMap.put("Product_type", Product_type);
        productMap.put("User_UUID", user_uuid);

        return productMap;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
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

    public String getProduct_type() {
        return Product_type;
    }

    public void setProduct_type(String product_type) {
        Product_type = product_type;
    }
}
