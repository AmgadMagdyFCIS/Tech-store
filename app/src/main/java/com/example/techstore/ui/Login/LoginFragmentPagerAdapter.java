package com.example.techstore.ui.Login;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.techstore.ui.Login.fragments.LoginFragment;
import com.example.techstore.ui.Login.fragments.RegistrationFragment;

import java.util.ArrayList;

public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments =new ArrayList<>();
    private ArrayList<String> fragmentTitle =new ArrayList<>();

    public LoginFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment ,String title)
    {
        fragments.add(fragment);
        fragmentTitle.add(title);
    }
}
