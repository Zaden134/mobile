package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.Donor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDonorScreen(
    donor: Donor,
    onBackClick: () -> Unit,
    onUpdateDonor: (Donor) -> Unit

) {
    var name by remember { mutableStateOf(donor.name) }
    var age by remember { mutableStateOf(donor.age.toString()) }
    var phone by remember { mutableStateOf(donor.phone) }
    var bloodGroup by remember { mutableStateOf(donor.bloodType) }
    var expanded by remember { mutableStateOf(false) }

    val groups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chỉnh sửa thông tin") },
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
            Text("Chỉnh sửa thông tin", style = MaterialTheme.typography.headlineMedium)

            // Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Age
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Tuổi") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Phone
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Số điện thoại") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Blood group dropdown
            Box {
                OutlinedTextField(
                    value = bloodGroup,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Nhóm máu") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Chọn nhóm máu")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    groups.forEach { g ->
                        DropdownMenuItem(
                            text = { Text(g) },
                            onClick = {
                                bloodGroup = g
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Update button
            Button(
                onClick = {
                    scope.launch {
                        val nameText = name.trim()
                        val ageText = age.trim()
                        val phoneText = phone.trim()
                        val bloodText = bloodGroup.trim()

                        when {
                            nameText.isBlank() || ageText.isBlank() || phoneText.isBlank() || bloodText.isBlank() -> {
                                snackbarHostState.showSnackbar("⚠️ Vui lòng nhập đủ thông tin")
                            }
                            ageText.toIntOrNull() == null || ageText.toInt() < 18 -> {
                                snackbarHostState.showSnackbar("❌ Tuổi phải từ 18 trở lên")
                            }
                            phoneText.length != 10 -> {
                                snackbarHostState.showSnackbar("❌ Số điện thoại phải đủ 10 số")
                            }
                            else -> {
                                val updatedDonor = donor.copy(
                                    name = nameText,
                                    age = ageText.toInt(),
                                    phone = phoneText,
                                    bloodType = bloodText
                                )
                                onUpdateDonor(updatedDonor)
                                snackbarHostState.showSnackbar("✅ Cập nhật thành công")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text("Cập nhật thông tin")
            }
        }
    }
}