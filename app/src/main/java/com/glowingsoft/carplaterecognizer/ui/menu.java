package com.glowingsoft.carplaterecognizer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.glowingsoft.carplaterecognizer.R;

public class menu extends AppCompatActivity {

    Button btnToMainActivity, btnToListActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnToMainActivity = findViewById(R.id.btnToMainActivity);
        btnToListActivity = findViewById(R.id.btnToListActivity);

        // Set onClickListener for going to MainActivity
        btnToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for going to ListActivity
        btnToListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, DaftarKendaraan.class);
                startActivity(intent);
            }
        });
    }
}