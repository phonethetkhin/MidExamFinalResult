package com.example.samplepj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.samplepj.R;
import com.example.samplepj.SamplePJDatabase;
import com.example.samplepj.model.UserModel;
import com.example.samplepj.util.SharedPrefs;
import com.example.samplepj.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    SamplePJDatabase db;
    TextInputLayout tiUserName, tiPassword, tiCPassword;
    TextInputEditText etUserName, etPassword, etCPasswrod;
    Button btRegister, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new SamplePJDatabase(this);


        initializeViews();

        handleClicks();

    }

    void initializeViews() {
        tiUserName = findViewById(R.id.tiUserName);
        tiPassword = findViewById(R.id.tiPassword);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        btRegister = findViewById(R.id.btRegister);
        btLogin = findViewById(R.id.btLogin);

    }

    private void handleClicks() {
        btRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
        btLogin.setOnClickListener(view -> {
            if (checkValidations()) {
                UserModel userModel = db.loginUser(etUserName.getText().toString(), etPassword.getText().toString());
                if (userModel != null) {
                    SharedPrefs.setBooleanPref(this, "isLoggedIn", true);
                    SharedPrefs.setStringPref(this, "userName", userModel.getUserName());
                    SharedPrefs.setIntPref(this, "userId", userModel.getUserId());

                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                } else {
                    Utils.showToast(this, "User name or password incorrect !!!");
                }
            }
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
        } else {
            return true;
        }
    }


}