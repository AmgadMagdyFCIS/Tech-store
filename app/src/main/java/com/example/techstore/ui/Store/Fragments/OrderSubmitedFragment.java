package com.example.techstore.ui.Store.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.RecyclerView.RecyclerViewAdapter;
import com.example.techstore.ui.Store.RecyclerView.Order;
import com.example.techstore.ui.Store.RecyclerView.ProductClickListener;
import com.example.techstore.ui.Store.StoreActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderSubmitedFragment  extends Fragment implements ProductClickListener {
    RecyclerView recyclerView;
    List<Order> orders;
    RecyclerViewAdapter shoppingCartAdapter;
    DbHelper dbHelper;
    String email;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_products, container, false);

        StoreActivity storeActivity= (StoreActivity) getActivity();
        email=storeActivity.getEmail();

        recyclerView = view.findViewById(R.id.recycler_view);
        textView = view.findViewById(R.id.categoryTitle);
        textView.setText("Your Orders.");

        orders = new ArrayList<>();

        dbHelper = new DbHelper(getActivity());
        Cursor cursor= dbHelper.returnOrders(email);

        if (cursor.getCount()>=1) {
            for (int i = 0; i < cursor.getCount(); i++) {
                orders.add(new Order(cursor.getInt(0),cursor.getInt(2),cursor.getString(1),cursor.getString(3) ,cursor.getString(4)));
                cursor.moveToNext();
            }
            cursor.close();
        }


        shoppingCartAdapter = new RecyclerViewAdapter(3,getActivity(), orders, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(shoppingCartAdapter);


        return view;
    }
    @Override
    public void onRecyclerViewClick(int pos, int i) {
        ProductFragment ldf = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt("id", orders.get(pos).getId());
        ldf.setArguments(args);

        getFragmentManager().beginTransaction().replace(R.id.frame_layout, ldf).addToBackStack(null).commit();
    }

    @Override
    public void onIncreaseClick(int pos) {

    }

    @Override
    public void onDecreaseClick(int pos) {

    }

    @Override
    public void onDeleteClick(int pos) {

    }
}
