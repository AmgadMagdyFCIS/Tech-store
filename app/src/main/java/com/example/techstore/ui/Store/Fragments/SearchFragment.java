package com.example.techstore.ui.Store.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.RecyclerView.RecyclerViewAdapter;
import com.example.techstore.ui.Store.RecyclerView.Product;
import com.example.techstore.ui.Store.RecyclerView.ProductClickListener;
import com.example.techstore.ui.Store.StoreActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment  extends Fragment implements ProductClickListener,View.OnClickListener {

    private final int VOICE_REQUEST = 1999;
    private EditText productName;
    private ImageButton search,voice;
    private RecyclerView recyclerView;
    private List<Product> products;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DbHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        productName=view.findViewById(R.id.search_product);
        voice=view.findViewById(R.id.voice_search);
        search=view.findViewById(R.id.search_button);
        products = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        dbHelper =new DbHelper(getActivity());

        voice.setOnClickListener(this);
        search.setOnClickListener(this);


        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), products, this,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.search_button:
                getproduct();
                break;
            case R.id.voice_search:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, VOICE_REQUEST);

                break;
        }
    }

    @Override
    public void onRecyclerViewClick(int pos, int i) {
        ProductFragment ldf = new ProductFragment();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    productName.setText(result.get(0));
                }
            }
        }
        getproduct();
    }
    void getproduct()
    {
        String pName=productName.getText().toString();
        products.clear();
        Cursor cursor=dbHelper.SearchByName(pName);
        for (int i=0;i<cursor.getCount();i++)
        {
            products.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3) ));
            cursor.moveToNext();
        }
        cursor.close();
        recyclerViewAdapter.notifyDataSetChanged();
    }
}