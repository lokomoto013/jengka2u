package com.example.jengka2u.model;

public class Product
{
    private String product_name, description , image , price , category, pid,date,time;

    public Product()

    {

    }

    public Product(String product_name, String description, String image, String price, String category, String pid, String date, String time)
    {
        this.product_name = product_name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
