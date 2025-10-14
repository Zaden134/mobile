package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.DonationRecord
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonationHistoryScreen(
    donorName: String,
    onBackClick: () -> Unit,
    onSaveDonation: (DonationRecord) -> Unit

) {
    var date by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var bloodAmount by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thêm lịch sử hiến máu") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                    titleContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Thêm lịch sử hiến máu cho $donorName",
                style = MaterialTheme.typography.headlineMedium
            )

            // Ngày hiến máu
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Ngày hiến máu") },
                placeholder = { Text("DD/MM/YYYY") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Địa điểm
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Địa điểm hiến máu") },
                placeholder = { Text("Ví dụ: Bệnh viện Chợ Rẫy") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Lượng máu
            OutlinedTextField(
                value = bloodAmount,
                onValueChange = { bloodAmount = it },
                label = { Text("Lượng máu (ml)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        when {
                            date.isBlank() || location.isBlank() || bloodAmount.isBlank() -> {
                                snackbarHostState.showSnackbar("⚠️ Vui lòng nhập đủ thông tin")
                            }
                            bloodAmount.toIntOrNull() == null -> {
                                snackbarHostState.showSnackbar("❌ Lượng máu phải là số")
                            }
                            else -> {
                                val donationRecord = DonationRecord(
                                    date = date,
                                    location = location,
                                    bloodAmount = bloodAmount.toInt()
                                )
                                onSaveDonation(donationRecord)
                                snackbarHostState.showSnackbar("✅ Đã thêm lịch sử hiến máu")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lưu lịch sử hiến máu")
            }
        }
    }
}