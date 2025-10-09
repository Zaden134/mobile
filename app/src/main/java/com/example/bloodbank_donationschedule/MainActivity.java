package com.example.bloodbank_donationschedule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister, btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btnRegister);
        btnCheck = findViewById(R.id.btnCheck);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        });

        btnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CheckStatusActivity.class);
            startActivity(intent);
        });
    }
}
