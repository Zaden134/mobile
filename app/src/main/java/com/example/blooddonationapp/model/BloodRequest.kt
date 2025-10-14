package com.example.blooddonationapp.model

data class BloodRequest(
    val id: Int,
    val patientName: String,
    val bloodType: String,
    val location: String,
    val requiredMl: Int,
    var status: String = "Pending", // "Pending", "Approved", "Rejected"
    val urgency: String = "Medium", // "Low", "Medium", "High", "Critical"
    val contactPhone: String = "",
    val hospital: String = "",
    val notes: String = ""
)