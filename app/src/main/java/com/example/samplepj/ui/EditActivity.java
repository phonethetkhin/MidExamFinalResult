package com.example.samplepj.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samplepj.R;
import com.example.samplepj.SamplePJDatabase;
import com.example.samplepj.util.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class EditActivity extends AppCompatActivity {
    TextInputEditText etStatus;
    Button btUpdate;
    SamplePJDatabase db;
    int statusId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = new SamplePJDatabase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Status");
        actionBar.setDisplayHomeAsUpEnabled(true);
        initViews();
        handleClicks();
        statusId = getIntent().getIntExtra("statusId", 0);
        String status = getIntent().getStringExtra("status");
        etStatus.setText(status);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        etStatus = findViewById(R.id.etStatus);

        btUpdate = findViewById(R.id.btUpdate);
    }

    private void handleClicks() {
        btUpdate.setOnClickListener(view -> {
            if (db.updateStatus(statusId, etStatus.getText().toString())) {
                Utils.showToast(this, "Status updated!!!");
                finish();
            } else {
                Utils.showToast(this, "Something went wrong");

            }
        });
    }
}