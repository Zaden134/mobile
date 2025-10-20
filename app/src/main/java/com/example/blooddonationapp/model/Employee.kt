package com.example.blooddonationapp.model

data class Employee(
    val id: Int,
    val name: String,
    val role: String, // "Nhân viên y tế", "Quản lý tại chỗ", "Cán bộ cấp cao"
    val department: String,
    val note: String = "",
    val phone: String = "",
    val email: String = "",
    val isBloodDonor: Boolean = false, // Nhân viên này có phải là người hiến máu không
    val donorId: String? = null // Liên kết với Donor nếu là người hiến máu
)