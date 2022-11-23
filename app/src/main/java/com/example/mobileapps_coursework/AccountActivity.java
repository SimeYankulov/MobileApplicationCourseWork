package com.example.mobileapps_coursework;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends DBActivity implements Validation {

    protected EditText editAUsername,editAPassword,editAEmail, editAPhone;
    protected Button updateAcc,deleteAcc,logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editAUsername = findViewById(R.id.editTextAUsername);
        editAPassword = findViewById(R.id.editTextAPassword);
        editAEmail = findViewById(R.id.editTextAEmail);
        editAPhone = findViewById(R.id.editTextAPhone);

        updateAcc = findViewById(R.id.buttonUpdate);
        deleteAcc = findViewById(R.id.buttonDelete);
        logOut = findViewById(R.id.buttonLogOut);

        try {
            SelectSQL(
                  "Select * from User where ID="+getLoggedid(),
                                null,
                            (id,Username,Password,Tel,Email)-> {
                                editAUsername.setText(Username);
                                editAPassword.setText(Password);
                                editAPhone.setText(Tel);
                                editAEmail.setText(Email);
                            }

            );
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(
                    getApplicationContext(), e.getLocalizedMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }

        //
        Validation.Validate(
                editAUsername,
                editAPassword,
                editAPassword,
                editAPhone,
                editAEmail,
                ()->updateAcc.setEnabled(true),
                ()->updateAcc.setEnabled(false)
        );
        TextWatcher watcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Validation.Validate(
                        editAUsername,
                        editAPassword,
                        editAPassword,
                        editAPhone,
                        editAEmail,
                        ()->updateAcc.setEnabled(true),
                        ()->updateAcc.setEnabled(false)
                );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editAUsername.addTextChangedListener(watcher);
        editAPassword.addTextChangedListener(watcher);
        editAEmail.addTextChangedListener(watcher);
        editAPhone.addTextChangedListener(watcher);

        deleteAcc.setOnClickListener(view->{
            try{
                ExecSql(
                        "DELETE FROM User " +
                                "WHERE ID = "+getLoggedid(),
                        null,
                        ()->Toast.makeText(getApplicationContext(),
                                        "DELETE SUCCEEDED", Toast.LENGTH_LONG)
                                .show()

                );
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }finally {
                BackToMain();
            }

        });


        updateAcc.setOnClickListener(view->{
            try{
                ExecSql(
                        "UPDATE User SET " +
                                "Username = ?, " +
                                "Password = ?, "+
                                "Tel = ?, " +
                                "Email = ? " +
                                "WHERE ID = ?",
                        new Object[]{
                                editAUsername.getText().toString(),
                                editAPassword.getText().toString(),
                                editAPhone.getText().toString(),
                                editAEmail.getText().toString(),
                                getLoggedid()
                        },
                        ()->Toast.makeText(getApplicationContext(),
                                        "UPDATE SUCCEEDED", Toast.LENGTH_LONG)
                                .show()

                );
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }finally {
                BackToMain();
            }

        });

        logOut.setOnClickListener(view -> {
            setLoggedid(0);
            BackToMain();
        });


    }

    private void BackToMain() {
        setLoggedid(0);
        finishActivity(200);
        Intent i = new Intent(AccountActivity.this, LoginActivity.class);
        startActivity(i);
    }
}