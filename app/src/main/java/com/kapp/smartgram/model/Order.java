package com.kapp.smartgram.model;

public class Order {
    String id;
    String user_id;
    String product_id;
    String method;
    String total;
    String quantity;
    String address;
    String mobile;
    String delivery_charges;
    String status;
    String category_id;
    String product_name;
    String price;
    String brand;
    String description;
    String image;
    String order_date;

    public Order(String id, String user_id, String product_id, String method, String total, String quantity, String address, String mobile, String delivery_charges, String status, String category_id, String product_name, String price, String brand, String description, String image, String order_date) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.method = method;
        this.total = total;
        this.quantity = quantity;
        this.address = address;
        this.mobile = mobile;
        this.delivery_charges = delivery_charges;
        this.status = status;
        this.category_id = category_id;
        this.product_name = product_name;
        this.price = price;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.order_date = order_date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}
