package com.example.samplepj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.samplepj.R;
import com.example.samplepj.SamplePJDatabase;
import com.example.samplepj.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    SamplePJDatabase db;
    TextInputLayout tiUserName, tiPassword, tiCPassword;
    TextInputEditText etUserName, etPassword, etCPasswrod;
    Button btRegister, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new SamplePJDatabase(this);
        initializeViews();

        handleClicks();
    }


    void initializeViews() {
        tiUserName = findViewById(R.id.tiUserName);
        tiPassword = findViewById(R.id.tiPassword);
        tiCPassword = findViewById(R.id.tiCPassword);


        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etCPasswrod = findViewById(R.id.etCPassword);

        btRegister = findViewById(R.id.btRegister);
        btLogin = findViewById(R.id.btLogin);

    }

    private void handleClicks() {
        btRegister.setOnClickListener(view -> {
            if (checkValidations()) {
                if (!(db.isUserExist(etUserName.getText().toString()))) {
                    db.insertUser(etUserName.getText().toString(), etPassword.getText().toString());
                    Utils.showToast(this, "Register successfully !!!");
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();

                } else {
                    Utils.showToast(this, "This username already exist !!!");
                }
            }
        });
        btLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private boolean checkValidations() {
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            tiUserName.setError("Enter UserName");
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            tiUserName.setError(null);
            tiPassword.setError("Enter password");
            return false;
        } else if (etPassword.getText().length() < 6) {
            tiPassword.setError("Password must be greater than 6");
            return false;
        } else if (!etPassword.getText().toString().equals(etCPasswrod.getText().toString())) {
            tiPassword.setError(null);
            tiCPassword.setError("Passwords dont' match");
            return false;
        } else {
            return true;
        }
    }


}