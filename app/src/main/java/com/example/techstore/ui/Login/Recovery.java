package com.example.techstore.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.ui.Store.StoreActivity;

public class Recovery extends AppCompatActivity {

    EditText email,phone,newPassword;
    Button recover;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        getSupportActionBar().hide();
        email=findViewById(R.id.email_text);
        phone=findViewById(R.id.phonenumber_text);
        newPassword=findViewById(R.id.password_text);
        dbHelper=new DbHelper(this);
        recover=findViewById(R.id.recover);
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validation=dbHelper.ValidateUser(email.getText().toString(),"",phone.getText().toString(),2);
                if (isEmail(email.getText().toString())) {
                    if (validation=="Valid for Recover") {
                        if (!newPassword.getText().toString().equals("")) {
                            dbHelper.RecoverPassword(email.getText().toString(), newPassword.getText().toString());
                            Toast.makeText(getApplicationContext(), "new password has been set", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(getApplicationContext(), StoreActivity.class);
                            login.putExtra("email",email.getText().toString());
                            startActivity(login);
                            finish();
                        } else {
                            newPassword.setError("Password is required!");
                        }
                    }
                    else if(validation=="Wrong Phone Number")
                        Toast.makeText(getApplicationContext(), "Wrong Phone Number", Toast.LENGTH_LONG).show();
                    else if(validation=="Not Valid")
                        email.setError("Enter a valid email!");
                }
                else
                    email.setError("Enter a valid email!");

            }
        });


    }


    private boolean isEmail(String mail) {
        return !mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }
}