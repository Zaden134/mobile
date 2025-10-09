package com.example.bloodbank_donationschedule;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CheckStatusActivity extends AppCompatActivity {

    private EditText edtName, edtPhone;
    private Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnCheck = findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(v -> checkStatus());
    }

    private void checkStatus() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (DonationData.phone != null && DonationData.phone.equals(phone)) {
            new AlertDialog.Builder(this)
                    .setTitle("Kết quả")
                    .setMessage("Xin chào " + name +
                            "\nBạn đã đăng ký hiến máu." +
                            "\nNhóm máu: " + DonationData.bloodGroup +
                            "\nNgày: " + DonationData.date +
                            "\nGiờ: " + DonationData.time +
                            "\nTrạng thái: " + DonationData.status)
                    .setPositiveButton("OK", (d, w) -> finish())
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Chưa đăng ký")
                    .setMessage("Bạn chưa đăng ký hiến máu. Bạn có muốn đăng ký ngay không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        Intent intent = new Intent(CheckStatusActivity.this, ScheduleActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Không", (dialog, which) -> finish())
                    .show();
        }
    }
}
