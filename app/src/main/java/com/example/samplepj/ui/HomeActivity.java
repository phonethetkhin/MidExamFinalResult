package com.example.samplepj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplepj.R;
import com.example.samplepj.SamplePJDatabase;
import com.example.samplepj.adapter.StatusAdapter;
import com.example.samplepj.model.StatusModel;
import com.example.samplepj.util.SharedPrefs;
import com.example.samplepj.util.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextInputEditText etStatus;
    Button btUpload;
    SamplePJDatabase db;
    RecyclerView rvStatus;
    boolean isFilter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new SamplePJDatabase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        Utils.showToast(this, "Welcome, " + (SharedPrefs.getStringPref(this, "userName")));
        initViews();
        handleClicks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter(db.getAllStatus(0, SharedPrefs.getIntPref(this, "userId")));
    }

    @Override
    public void onBackPressed() {
        showDialog("Are you sure, you want to exit?", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_filter: {
                if (!isFilter) {
                    isFilter = true;
                    item.setIcon(R.drawable.ic_baseline_filter_list_off_24);
                    setAdapter(db.getAllStatus(1, SharedPrefs.getIntPref(this, "userId")));

                } else {
                    isFilter = false;
                    item.setIcon(R.drawable.ic_baseline_filter_list_24);
                    setAdapter(db.getAllStatus(0, SharedPrefs.getIntPref(this, "userId")));

                }
                break;
            }

            case R.id.nav_logout: {
                showDialog("Are you sure, you want to logout?", true);
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        etStatus = findViewById(R.id.etStatus);

        btUpload = findViewById(R.id.btUpload);

        rvStatus = findViewById(R.id.rvStatus);
    }

    private void handleClicks() {
        btUpload.setOnClickListener(view -> {
            if (db.insertStatus(SharedPrefs.getIntPref(this, "userId"), etStatus.getText().toString())) {
                Utils.showToast(this, "Status uploaded");
                setAdapter(db.getAllStatus(0, SharedPrefs.getIntPref(this, "userId")));
            } else {
                Utils.showToast(this, "Something went wrong");
            }
        });
    }

    private void setAdapter(List<StatusModel> statusModelList) {
        StatusAdapter adapter = new StatusAdapter(this, db, statusModelList);
        rvStatus.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rvStatus.setAdapter(adapter);
    }

    private void showDialog(String message, boolean isLogout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            if (isLogout) {
                SharedPrefs.clearPrefs(this);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}