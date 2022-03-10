package com.example.techstore.ui.Store.RecyclerView;

public interface ProductClickListener {
    void onRecyclerViewClick(int pos,int i);
    void onIncreaseClick(int pos);
    void onDecreaseClick(int pos);
    void onDeleteClick(int pos);
}

