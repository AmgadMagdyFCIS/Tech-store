package com.example.techstore.ui.Store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.techstore.R;
import com.example.techstore.ui.Store.Fragments.AccountFragment;
import com.example.techstore.ui.Store.Fragments.HomeFragment;
import com.example.techstore.ui.Store.Fragments.OrderSubmitedFragment;
import com.example.techstore.ui.Store.Fragments.SearchFragment;
import com.example.techstore.ui.Store.Fragments.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getSupportActionBar().hide();

        email=getIntent().getExtras().getString("email");
        int i=getIntent().getExtras().getInt("order");

        bottomNavigation=findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        if(i==1) {
            bottomNavigation.setSelectedItemId(R.id.order);
            transaction(new OrderSubmitedFragment());
        }
        else if(i==2) {
            bottomNavigation.setSelectedItemId(R.id.shopping_cart);
            transaction(new ShoppingCartFragment());
        }
        else {
            bottomNavigation.setSelectedItemId(R.id.home);
            transaction(new HomeFragment());
        }




        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        transaction(new HomeFragment());
                        break;
                    case R.id.account:
                        transaction(new AccountFragment());
                        break;
                    case R.id.shopping_cart:
                        transaction(new ShoppingCartFragment());
                        break;
                    case R.id.order:
                        transaction(new OrderSubmitedFragment());
                        break;
                    case R.id.search:
                        transaction(new SearchFragment());
                        break;

                }
                return true;
            }
        });
    }
    public void transaction(Fragment fragment)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);//f
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public String getEmail() {
        return email;
    }
    @Override
    public void onBackPressed() {

            android.app.FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }

    }

}