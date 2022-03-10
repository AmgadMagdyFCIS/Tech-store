package com.example.techstore.ui.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.techstore.R;
import com.example.techstore.ui.Login.fragments.LoginFragment;
import com.example.techstore.ui.Login.fragments.RegistrationFragment;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    LoginFragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        adapter=new LoginFragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new LoginFragment(),"Login");
        adapter.addFragment(new RegistrationFragment(),"Register");

        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


    }

}