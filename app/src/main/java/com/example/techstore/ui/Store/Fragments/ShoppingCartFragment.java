package com.example.techstore.ui.Store.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.MapsActivity;
import com.example.techstore.ui.Store.RecyclerView.RecyclerViewAdapter;
import com.example.techstore.ui.Store.RecyclerView.Product;
import com.example.techstore.ui.Store.RecyclerView.ProductClickListener;
import com.example.techstore.ui.Store.StoreActivity;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartFragment extends Fragment implements ProductClickListener {

    Button submit;
    RecyclerView recyclerView;
    List<Product> products;
    RecyclerViewAdapter shoppingCartAdapter;
    DbHelper dbHelper;
    String email;
    int orderId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        StoreActivity storeActivity= (StoreActivity) getActivity();
        email=storeActivity.getEmail();
        recyclerView = view.findViewById(R.id.recyclerViewShoppingCart);

        products = new ArrayList<>();

        dbHelper = new DbHelper(getActivity());
        Cursor cursor= dbHelper.returnCartItems(email);

        if(cursor!=null) {
            if (cursor.getCount() >= 1) {
                orderId = cursor.getInt(7);
                for (int i = 0; i < cursor.getCount(); i++) {
                    products.add(new Product(cursor.getInt(0), cursor.getString(1),cursor.getInt(4) ,cursor.getInt(9), cursor.getString(2), cursor.getString(3), cursor.getInt(7)));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

        shoppingCartAdapter = new RecyclerViewAdapter(getActivity(), products, this,2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(shoppingCartAdapter);


        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = 0;
                for (int i = 0; i < products.size(); i++) {
                    totalPrice += Double.valueOf(products.get(i).getPrice()) * products.get(i).getQuantity();
                }
                Intent i=new Intent(getActivity(), MapsActivity.class);
                i.putExtra("orderId",orderId);
                i.putExtra("totalPrice",totalPrice);
                i.putExtra("email",email);
                getActivity().startActivity(i);
                getActivity().finish();
            }
        });



        return view;
    }


    @Override
    public void onRecyclerViewClick(int pos,int i) {

    }

    @Override
    public void onIncreaseClick(int pos) {
        if(products.get(pos).getStockQuantity()>1) {
            dbHelper.increaseQuantity(products.get(pos).getId(), products.get(pos).getOrderId(), products.get(pos).getQuantity());
            products.get(pos).increasequantety();
            shoppingCartAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(getContext(), products.get(pos).getName().toString() + "out of stock", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDecreaseClick(int pos) {
        if(products.get(pos).getQuantity()>1) {
            dbHelper.decreaseQuantity(products.get(pos).getId(), products.get(pos).getOrderId(),products.get(pos).getQuantity());
            products.get(pos).decreasequantety();
            shoppingCartAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDeleteClick(int pos) {
        Toast.makeText(getContext(), products.get(pos).getName() + " deleted from shopping cart", Toast.LENGTH_LONG).show();
        dbHelper.deleteItem(products.get(pos).getId(),products.get(pos).getOrderId());
        products.remove(pos);
        shoppingCartAdapter.notifyDataSetChanged();

    }
}