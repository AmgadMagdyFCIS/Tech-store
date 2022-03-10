package com.example.techstore.ui.Login.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.Fragments.AccountFragment;
import com.example.techstore.ui.Store.StoreActivity;

import java.util.Calendar;

public class RegistrationFragment extends Fragment {

    EditText name,email,password,date,phone;
    DatePickerDialog picker;
    Button register;

    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        name=view.findViewById(R.id.username_text);
        email=view.findViewById(R.id.email_text);
        password=view.findViewById(R.id.password_text);
        date=view.findViewById(R.id.birthdate_text);
        phone=view.findViewById(R.id.phonenumber_text);
        register=view.findViewById(R.id.register);
        dbHelper=new DbHelper(getActivity());
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateData()) {
                    if(dbHelper.checkemail(email.getText().toString()))
                    {
                        Toast.makeText(getActivity(), "Email exists", Toast.LENGTH_LONG).show();
                    }
                    else {
                        dbHelper.signUp(name.getText().toString(), email.getText().toString(), password.getText().toString(), date.getText().toString(), phone.getText().toString());

                        Toast.makeText(getActivity(), "Registration Completed", Toast.LENGTH_LONG).show();
                        ViewPager mviewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                        mviewPager.setCurrentItem(0);
                    }
                }
            }
        });


        return view;
    }
    private boolean ValidateData()
    {
        boolean isValid = true;
        if(name.getText().toString().equals(""))
        {
            name.setError("You must enter username!");
            isValid = false;
        }
        if(!isEmail(email.getText().toString()))
        {
            email.setError("Please enter a valid email address");
            isValid = false;
        }
        if (password.getText().toString().equals(""))
        {
            password.setError("You must enter a password!");
            isValid = false;
        }
        if(date.getText().toString().equals(""))
        {
            date.setError("You must enter your birthdate!");
            isValid = false;
        }

        return isValid;

    }
    private boolean isEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}