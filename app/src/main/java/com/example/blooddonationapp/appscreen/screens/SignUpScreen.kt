package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.Donor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpSuccess: (Donor) -> Unit,
    onNavigateToList: () -> Unit,
    onBackClick: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val groups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đăng ký hiến máu") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Đăng ký hiến máu", style = MaterialTheme.typography.headlineMedium)

            // Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Họ và tên") },
                placeholder = { Text("Nhập họ tên...") },
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

            // Blood group (read-only dropdown)
            Box {
                OutlinedTextField(
                    value = bloodGroup,
                    onValueChange = { /* noop, only choose from list */ },
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

            // Submit button - ĐÃ CẬP NHẬT
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
                                snackbarHostState.showSnackbar("❌ Bạn chưa đủ 18 tuổi để hiến máu")
                            }
                            phoneText.length < 10 -> {
                                snackbarHostState.showSnackbar("❌ Số điện thoại chưa đủ 10 số")
                            }
                            phoneText.length > 10 -> {
                                snackbarHostState.showSnackbar("❌ Số điện thoại không được quá 10 số")
                            }
                            else -> {
                                val newDonor = Donor(
                                    name = nameText,
                                    age = ageText.toInt(),
                                    phone = phoneText,
                                    bloodType = bloodText,
                                    donationHistory = emptyList()
                                )
                                onSignUpSuccess(newDonor)
                                snackbarHostState.showSnackbar("✅ Đăng ký thành công: $nameText - $ageText tuổi - nhóm $bloodText")

                                // Clear form
                                name = ""
                                age = ""
                                phone = ""
                                bloodGroup = ""
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text(
                    "ĐĂNG KÝ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Navigation to List - ĐÃ CẬP NHẬT
            OutlinedButton(
                onClick = onNavigateToList,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "Xem danh sách người hiến máu",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}