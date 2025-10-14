package com.example.blooddonationapp.model

data class Donor(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val phone: String = "",
    val bloodType: String = "",
    val donationHistory: List<DonationRecord> = emptyList()
)

data class DonationRecord(
    val date: String = "",
    val location: String = "",
    val bloodAmount: Int = 0
)