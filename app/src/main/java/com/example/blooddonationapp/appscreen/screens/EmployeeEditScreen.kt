package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.Employee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeEditScreen(
    employee: Employee? = null,
    onSave: (String, String, String, String, String, String, Boolean, String) -> Unit,
    onCancel: () -> Unit,

) {
    var name by remember { mutableStateOf(employee?.name ?: "") }
    var role by remember { mutableStateOf(employee?.role ?: "") }
    var department by remember { mutableStateOf(employee?.department ?: "") }
    var note by remember { mutableStateOf(employee?.note ?: "") }
    var phone by remember { mutableStateOf(employee?.phone ?: "") }
    var email by remember { mutableStateOf(employee?.email ?: "") }

    // Thêm State hiến máu
    var isBloodDonor by remember { mutableStateOf(employee?.isBloodDonor ?: false) }
    var donorId by remember { mutableStateOf(employee?.donorId ?: "") }

    var roleExpanded by remember { mutableStateOf(false) }
    var bloodDonorExpanded by remember { mutableStateOf(false) } // THÊM STATE
    var departmentExpanded by remember { mutableStateOf(false) }


    val roles = listOf("Nhân viên y tế", "Quản lý tại chỗ", "Cán bộ cấp cao")
    val bloodDonorOptions = listOf("Chưa đăng ký hiến máu", "Đã đăng ký hiến máu") // THÊM OPTIONS
    val departments = listOf( // THÊM DANH SÁCH PHÒNG BAN
        "Khoa Huyết học",
        "Khoa Cấp cứu",
        "Khoa Nội tổng quát",
        "Khoa Ngoại tổng quát",
        "Phòng Tổ chức",
        "Phòng Nhân sự",
        "Ban Giám đốc",
        "Phòng Kế hoạch",
        "Phòng Tài chính",
        "Khoa Xét nghiệm"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (employee == null) "Thêm nhân viên" else "Chỉnh sửa nhân viên") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                if (employee == null) "Thêm nhân viên mới" else "Chỉnh sửa thông tin nhân viên",
                style = MaterialTheme.typography.headlineMedium
            )

            // Tên nhân viên
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tên nhân viên") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Vai trò dropdown
            Box {
                OutlinedTextField(
                    value = role,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Vai trò") },
                    trailingIcon = {
                        IconButton(onClick = { roleExpanded = !roleExpanded }) {
                            Icon(
                                Icons.Filled.ArrowDropDown, // SỬA: Dùng Icons.Filled.ArrowDropDown
                                contentDescription = "Chọn vai trò"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = roleExpanded,
                    onDismissRequest = { roleExpanded = false }
                ) {
                    roles.forEach { r ->
                        DropdownMenuItem(
                            text = { Text(r) },
                            onClick = {
                                role = r
                                roleExpanded = false
                            }
                        )
                    }
                }
            }

            // THÊM: Phòng ban dropdown
            Box {
                OutlinedTextField(
                    value = department,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Phòng ban") },
                    trailingIcon = {
                        IconButton(onClick = { departmentExpanded = !departmentExpanded }) {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Chọn phòng ban"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = departmentExpanded,
                    onDismissRequest = { departmentExpanded = false }
                ) {
                    departments.forEach { dept ->
                        DropdownMenuItem(
                            text = { Text(dept) },
                            onClick = {
                                department = dept
                                departmentExpanded = false
                            }
                        )
                    }
                }
            }

            // Số điện thoại
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // THÊM: Dropdown Thông tin hiến máu
            Box {
                OutlinedTextField(
                    value = if (isBloodDonor) "Đã đăng ký hiến máu" else "Chưa đăng ký hiến máu",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Thông tin hiến máu") },
                    trailingIcon = {
                        IconButton(onClick = { bloodDonorExpanded = !bloodDonorExpanded }) {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Chọn trạng thái hiến máu"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = bloodDonorExpanded,
                    onDismissRequest = { bloodDonorExpanded = false }
                ) {
                    bloodDonorOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                isBloodDonor = (option == "Đã đăng ký hiến máu")
                                bloodDonorExpanded = false
                            }
                        )
                    }
                }
            }
            // THÊM: Ô nhập ID người hiến máu (chỉ hiện khi đã đăng ký)
            if (isBloodDonor) {
                OutlinedTextField(
                    value = donorId,
                    onValueChange = { donorId = it },
                    label = { Text("ID người hiến máu") },
                    placeholder = { Text("Nhập ID liên kết với Donor") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            // Ghi chú
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Ghi chú") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (name.isNotBlank() && role.isNotBlank() && department.isNotBlank()) {
                            onSave(name, role, department, note, phone, email,isBloodDonor,donorId)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = name.isNotBlank() && role.isNotBlank() && department.isNotBlank()
                ) {
                    Text("Lưu")
                }

                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Hủy")
                }
            }
        }
    }
}