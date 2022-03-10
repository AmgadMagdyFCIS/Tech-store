package com.example.techstore.ui.Store.RecyclerView;

public class Order {
    private int id;
    private int user_id;
    private String date;
    private String address;
    private String total_price;
    public Order(int id, int user_id , String date, String address, String total_price) {
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.address = address;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
