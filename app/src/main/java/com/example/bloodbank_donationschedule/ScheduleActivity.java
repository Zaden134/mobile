package com.example.bloodbank_donationschedule;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    private EditText edtName, edtPhone;
    private Spinner spnBloodGroup;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        spnBloodGroup = findViewById(R.id.spnBloodGroup);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnSchedule = findViewById(R.id.btnSchedule);

        // Spinner nhóm máu
        String[] bloodGroups = {"A", "B", "AB", "O"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodGroup.setAdapter(adapter);

        btnSchedule.setOnClickListener(v -> validateAndConfirm());
    }

    private void validateAndConfirm() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String bloodGroup = spnBloodGroup.getSelectedItem().toString();

        // Lấy ngày giờ chọn
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day, hour, minute);

        Calendar now = Calendar.getInstance();

        // Kiểm tra
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() != 10) {
            Toast.makeText(this, "Số điện thoại phải đủ 10 số", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDate.before(now)) {
            Toast.makeText(this, "Ngày giờ phải ở tương lai", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = day + "/" + (month + 1) + "/" + year;
        String time = hour + ":" + (minute < 10 ? "0" + minute : minute);

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Tên: " + name +
                        "\nSĐT: " + phone +
                        "\nNhóm máu: " + bloodGroup +
                        "\nNgày: " + date +
                        "\nGiờ: " + time)
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    DonationData.name = name;
                    DonationData.phone = phone;
                    DonationData.bloodGroup = bloodGroup;
                    DonationData.date = date;
                    DonationData.time = time;
                    DonationData.status = "Đang chờ xác nhận";

                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // về MainActivity
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
