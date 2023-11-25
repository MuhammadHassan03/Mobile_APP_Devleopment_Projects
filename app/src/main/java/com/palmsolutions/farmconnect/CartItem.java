package com.palmsolutions.farmconnect;

public class CartItem {
    String Title;
    String Count;
    String Price;
    String product_image;
    String product_type;
    String product_id;
    String cart_product_id;

    public CartItem(){

    }
    public CartItem(String title, String count, String price, String product_image, String product_type, String product_id, String cart_product_id) {
        Title = title;
        Count = count;
        Price = price;
        this.product_image = product_image;
        this.product_type = product_type;
        this.product_id = product_id;
        this.cart_product_id = cart_product_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCart_product_id() {
        return cart_product_id;
    }

    public void setCart_product_id(String cart_product_id) {
        this.cart_product_id = cart_product_id;
    }
}
