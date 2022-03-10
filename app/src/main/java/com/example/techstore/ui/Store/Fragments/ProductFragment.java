package com.example.techstore.ui.Store.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.StoreActivity;

public class ProductFragment extends Fragment {

    TextView name,price;
    Button add;
    ImageView imageView;
    DbHelper dbHelper;
    int id ,quantity;
    String email;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_product, container, false);
        StoreActivity storeActivity= (StoreActivity) getActivity();
        email=storeActivity.getEmail();

        int productId = getArguments().getInt("id");
        name =view.findViewById(R.id.item_name);
        price=view.findViewById(R.id.item_price);
        add=view.findViewById(R.id.add);
        imageView=view.findViewById(R.id.product_image);

        dbHelper=new DbHelper(getActivity());
        Cursor cursor=dbHelper.returnProductById(productId);

        id=cursor.getInt(0);
        name.setText(cursor.getString(1));
        price.setText(cursor.getString(2));
        int resourceIdentifier = view.getResources().getIdentifier(cursor.getString(3), "drawable", view.getContext().getPackageName());
        imageView.setImageResource(resourceIdentifier);
        quantity=cursor.getInt(4);
        cursor.close();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setVisibility(view.INVISIBLE);
                if(quantity>1) {
                    if(!dbHelper.AddItem(id,email))
                        Toast.makeText(getContext(), name.getText().toString() + " Added to shopping cart", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(), name.getText().toString() + " is already in your cart", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getContext(), name.getText().toString() + " out of stock", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
}