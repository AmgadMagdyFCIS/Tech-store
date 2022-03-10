package com.example.techstore.ui.Store.RecyclerView;

public class Product {

    private int id;
    private String name;
    private String category;
    private String price;
    private String Image;
    private int quantity;
    private int stockQuantity;
    private int orderId;



    public Product(int id, String name, String price, String Image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.Image = Image;

    }

    public Product(int id, String name,int stockQuantity, int quantity, String price, String Image,int orderId) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.Image = Image;
        this.stockQuantity=stockQuantity;
        this.quantity=quantity;
        this.orderId=orderId;
    }



    public int getId() {
        return id;
    }

    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void increasequantety()
    {
        quantity++;
        stockQuantity--;
    }
    public void decreasequantety()
    {
        quantity--;
        stockQuantity++;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
