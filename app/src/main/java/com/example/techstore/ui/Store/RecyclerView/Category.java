package com.example.techstore.ui.Store.RecyclerView;

public class Category {
    private int id;
    private String name;
    private String Image;

    public Category(int id, String name, String Image) {
        this.id = id;
        this.name = name;
        this.Image = Image;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return Image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        Image = image;
    }
}
