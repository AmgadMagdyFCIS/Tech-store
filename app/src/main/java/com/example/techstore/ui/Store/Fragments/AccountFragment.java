package com.example.techstore.ui.Store.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Login.Login;
import com.example.techstore.ui.Store.StoreActivity;

import java.util.Calendar;


public class AccountFragment extends Fragment implements View.OnClickListener {


    EditText name,email,password,birthDate,phone,jop;
    DatePickerDialog picker;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button update,logout;
    String _email;
    DbHelper dbHelper;
    View view;
    Cursor cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_account, container, false);
        name=view.findViewById(R.id.username_text);
        email=view.findViewById(R.id.email_text);
        password=view.findViewById(R.id.password_text);
        birthDate=view.findViewById(R.id.birthdate_text);
        phone=view.findViewById(R.id.phonenumber_text);
        update=view.findViewById(R.id.update);
        logout=view.findViewById(R.id.logout);
        jop=view.findViewById(R.id.jop_text);
        radioGroup=view.findViewById(R.id.radioGroup);

        birthDate.setOnClickListener((View.OnClickListener) this);

        StoreActivity storeActivity= (StoreActivity) getActivity();
        _email=storeActivity.getEmail();
        dbHelper=new DbHelper(getActivity());
        if(cursor!=null)
        {
            cursor.close();
        }
        cursor=dbHelper.getUserByEmail(_email);
        if(cursor!=null)
        {
            name.setText(cursor.getString(1));
            email.setText(cursor.getString(2));
            password.setText(cursor.getString(3));
            birthDate.setText(cursor.getString(4));
            phone.setText(cursor.getString(5));
            jop.setText(cursor.getString(7));
            if(cursor.getString(6).equals("Male"))
            {
                radioGroup.check(R.id.male);
            }
            else if(cursor.getString(6).equals("Female"))
            {
                radioGroup.check(R.id.female);
            }
        }

        update.setOnClickListener((View.OnClickListener) this);
        logout.setOnClickListener((View.OnClickListener) this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.birthdate_text:
                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.update:
                String radio=check();
                dbHelper.editAccount(name.getText().toString(),_email,
                        email.getText().toString(),
                        password.getText().toString(),
                        birthDate.getText().toString(),
                        phone.getText().toString(),
                        radio,
                        jop.getText().toString());

                if(email.getText().toString().equals(_email))
                    getFragmentManager().beginTransaction().replace(R.id.frame_layout, new AccountFragment()).commit();
                else {
                    logOut();
                }
                break;
            case R.id.logout:
                logOut();
                break;
        }

    }
    public void logOut()
    {
        Intent i =new Intent(getActivity(), Login.class);
        SharedPreferences preferences =getActivity().getSharedPreferences("com.example.techstore.hellosharedprefs",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        startActivity(i);
        getActivity().finish();
    }
    public String check()
    {
        String radio;
        int radioId=radioGroup.getCheckedRadioButtonId();
        if(radioId!=-1) {
            radioButton = view.findViewById(radioId);
            radio =radioButton.getText().toString();
        }
        else
            radio="";
        return (radio);
    }

}