package com.example.techstore.ui.Store.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.RecyclerView.Category;
import com.example.techstore.ui.Store.RecyclerView.RecyclerViewAdapter;
import com.example.techstore.ui.Store.RecyclerView.Product;
import com.example.techstore.ui.Store.RecyclerView.ProductClickListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements ProductClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerView;
    private ArrayList<Category> categories;
    private ArrayList<Product> products;
    private RecyclerViewAdapter recyclerViewAdapter;
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        recyclerView = view.findViewById(R.id.recycler_view);

        categories = new ArrayList<>();
        products=new ArrayList<>();

        dbHelper = new DbHelper(getActivity());
        Cursor cursor = dbHelper.returnCategories();
        for (int i=0;i<cursor.getCount();i++)
        {
            categories.add(new Category(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), categories, (ProductClickListener) this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6));


        recyclerView.setAdapter(recyclerViewAdapter);


        Cursor cursor1 = dbHelper.returnBestSeller();
        if (cursor1!=null) {
            for (int i = 0; i < cursor1.getCount(); i++) {
                products.add(new Product(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), cursor1.getString(3)));
                cursor1.moveToNext();
            }
        }
        cursor1.close();

        recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), products, (ProductClickListener) this,1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        recyclerView.setAdapter(recyclerViewAdapter);


        return view;
    }

    @Override
    public void onRecyclerViewClick(int pos ,int i) {
        Fragment fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        if (i != 1) {
            if (pos == 0)
                fragment = new LaptopFragment();
            else if (pos == 1)
                fragment = new PhoneFragment();
            else
                fragment = new MonitorFragment();
        }
        else
        {
            fragment = new ProductFragment();
            Bundle args = new Bundle();
            args.putInt("id", products.get(pos).getId());
            fragment.setArguments(args);
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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