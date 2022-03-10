package com.example.techstore.ui.Login.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Login.Login;
import com.example.techstore.ui.Login.Recovery;
import com.example.techstore.ui.Store.StoreActivity;

public class LoginFragment extends Fragment {

    EditText email ,password,phone;
    TextView forgetPassword;
    CheckBox rememberMe;
    Button loginButton;
    DbHelper dbHelper;
    SharedPreferences loginPrefrence;
    SharedPreferences.Editor loginPrefrenceEditor;
    private String sharedPrefFile ="com.example.techstore.hellosharedprefs";

    String _email,_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        forgetPassword=view.findViewById(R.id.forgetpassword);
        email =view.findViewById(R.id.email_text);
        password=view.findViewById(R.id.password_text);
        phone=view.findViewById(R.id.phone_number);
        rememberMe=view.findViewById(R.id.remember);
        loginButton=view.findViewById(R.id.login);
        dbHelper=new DbHelper(getActivity());

        loginPrefrence =getActivity().getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        loginPrefrenceEditor=loginPrefrence.edit();

        if(loginPrefrence.getString("saveLogin" ,null)!=null)
        {
            _email = loginPrefrence.getString("Email", "");
            _password = loginPrefrence.getString("Password", "");
            String validation=dbHelper.ValidateUser(_email,_password,"",1);
            if(validation=="Valid") {
                Intent loginIntent = new Intent(getActivity(), StoreActivity.class);
                loginIntent.putExtra("email",_email);
                startActivity(loginIntent);
                getActivity().finish();
            }
            else
            {
                Toast.makeText(getActivity(),"Email not found",Toast.LENGTH_SHORT).show();
            }

        }

            loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _email=email.getText().toString();
                _password=password.getText().toString();
                String validation=dbHelper.ValidateUser(_email,_password,"",1);
                if(chceckData() &&validation=="Valid" )
                {
                    checkRemeberMe();
                    Toast.makeText(getActivity(),"Correct Information",Toast.LENGTH_LONG).show();

                    Intent loginIntent = new Intent(getActivity(), StoreActivity.class);
                    loginIntent.putExtra("email",_email);
                    startActivity(loginIntent);
                    getActivity().finish();

                }
                else if(validation=="Not Valid")
                    Toast.makeText(getActivity(),"Email not found",Toast.LENGTH_SHORT).show();

                else if(validation=="Wrong Password"&& !_password.isEmpty())
                    Toast.makeText(getActivity(),"Wrong Password",Toast.LENGTH_SHORT).show();

            }
        });




        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recoverPassword = new Intent(getActivity(), Recovery.class);
                startActivity(recoverPassword);
            }
        });

        return view;
    }

    private void checkRemeberMe()
    {
        if(rememberMe.isChecked())
        {
            if(loginPrefrence.getString("saveLogin" ,null)!=null)
            {
                loginPrefrenceEditor.clear();
                loginPrefrenceEditor.apply();
            }
            loginPrefrenceEditor.putString("saveLogin","true");
            loginPrefrenceEditor.putString("Email" , email.getText().toString());
            loginPrefrenceEditor.putString("Password",password.getText().toString());
            loginPrefrenceEditor.commit();
            loginPrefrenceEditor.apply();

            Toast.makeText(getActivity(),"Checked",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loginPrefrenceEditor.clear();
            loginPrefrenceEditor.apply();

            Toast.makeText(getActivity(),"Unchecked",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean chceckData()
    {
        boolean isValid = true;
        if(!isEmail(email.getText().toString()))
        {
            email.setError("Valid email is required!");
            isValid = false;
        }

        if(password.getText().toString().equals(""))
        {
            password.setError("Password is required!");
            isValid = false;
        }

        return isValid;
    }
    private boolean isEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}