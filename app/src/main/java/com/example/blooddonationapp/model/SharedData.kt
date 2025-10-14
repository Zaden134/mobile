package com.example.blooddonationapp.model

// Class để quản lý dữ liệu chung
data class AppData(
    val donors: List<Donor>,
    val employees: List<Employee>,
    val bloodDonationEvents: List<BloodDonationEvent>
)

data class BloodDonationEvent(
    val id: String,
    val date: String,
    val location: String,
    val organizer: String, // Nhân viên tổ chức
    val participants: List<String> // Danh sách donorId tham gia
)