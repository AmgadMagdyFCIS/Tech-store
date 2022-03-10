package com.example.techstore.ui.Store.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.RecyclerView.RecyclerViewAdapter;
import com.example.techstore.ui.Store.RecyclerView.Product;
import com.example.techstore.ui.Store.RecyclerView.ProductClickListener;

import java.util.ArrayList;

public class MonitorFragment extends Fragment implements ProductClickListener {
    TextView textView;
    private RecyclerView recyclerView;
    private ArrayList<Product> products;
    private RecyclerViewAdapter recyclerViewAdapter;
    DbHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_products, container, false);

        textView = view.findViewById(R.id.categoryTitle);
        textView.setText("Monitors");

        recyclerView = view.findViewById(R.id.recycler_view);

        products = new ArrayList<>();

        dbHelper = new DbHelper(getActivity());
        Cursor cursor = dbHelper.returnProductsByCategory(3);
        for (int i=0;i<cursor.getCount();i++)
        {
            products.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3) ));
            cursor.moveToNext();
        }
        cursor.close();

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), products, this,1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onRecyclerViewClick(int pos,int i) {
        ProductFragment ldf = new ProductFragment ();
        Bundle args = new Bundle();
        args.putInt("id", products.get(pos).getId());
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
