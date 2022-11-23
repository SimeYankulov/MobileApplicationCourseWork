package com.example.mobileapps_coursework;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends DBActivity implements Validation {

    protected EditText editRUsername,editRPassword,editConfirmPassword,editEmail, editPhone;
    protected Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editRUsername = findViewById(R.id.editTextRUsername);
        editRPassword = findViewById(R.id.editTextRPassword);
        editConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editEmail = findViewById(R.id.editTextREmail);
        editPhone = findViewById(R.id.editTextRPhone);
        btnRegister = findViewById(R.id.buttonRegister);

        Validation.Validate(
                editRUsername,
                editRPassword,
                editConfirmPassword,
                editPhone,
                editEmail,
                ()-> btnRegister.setEnabled(true),
                ()-> btnRegister.setEnabled(false)
        );
        TextWatcher watcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Validation.Validate(
                        editRUsername,
                        editRPassword,
                        editConfirmPassword,
                        editPhone,
                        editEmail,
                        ()-> btnRegister.setEnabled(true),
                        ()-> btnRegister.setEnabled(false)
                );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editRUsername.addTextChangedListener(watcher);
        editRPassword.addTextChangedListener(watcher);
        editConfirmPassword.addTextChangedListener(watcher);
        editPhone.addTextChangedListener(watcher);
        editEmail.addTextChangedListener(watcher);

        btnRegister.setOnClickListener(view->{
            try{
                ExecSql(
                        "INSERT INTO USER(Username, Password,Tel, Email) " +
                                "VALUES(?, ?, ?, ?)",
                        new Object[]{
                                editRUsername.getText().toString(),
                                editRPassword.getText().toString(),
                                editPhone.getText().toString(),
                                editEmail.getText().toString()
                        },
                        ()-> Toast.makeText(getApplicationContext(),
                                "Register Successful",
                                Toast.LENGTH_LONG
                        ).show()
                );
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        e.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }

            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }
}