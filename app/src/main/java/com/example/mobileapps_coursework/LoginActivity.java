package com.example.mobileapps_coursework;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends DBActivity {

    protected EditText editUsername,editPassword;
    protected Button btnLogin,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editTextUsername);
        editPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.loginbtn);
        btnRegister = findViewById(R.id.registerbtn);

        try {
            InitDB();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    e.getLocalizedMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try {
                        if(editUsername.getText().length() > 0 & editPassword.getText().length() >0) {
                            SelectSQL("Select * from User where Username = ? and Password = ?",
                                    new String[]{editUsername.getText().toString()
                                            , editPassword.getText().toString()},
                                    (ID, Username, Password, Phone, Email) ->
                                            Func(ID)
                                            //startActivity(new Intent(LoginActivity.this, MainActivity.class))

                            );

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                e.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }



            }
        });


    }
    void Func(String str){
        setLoggedid(Integer.parseInt(str));
        startActivity(new Intent(
                LoginActivity.this, MainActivity.class));
    }
}