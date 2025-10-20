package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.Donor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationDetailScreen(
    donor: Donor,
    donorIndex: Int,
    onBackClick: () -> Unit,
    onAddDonationClick: (Int) -> Unit,
    onEditDonorClick: (Int) -> Unit,
    onDeleteDonorClick: (Int) -> Unit

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Chi tiết người hiến",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header với avatar và nhóm máu
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = Color(0xFFFFCDD2),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = donor.name.first().toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        donor.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Badge nhóm máu
                    Surface(
                        color = Color(0xFFD32F2F),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = donor.bloodType,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Card thông tin cá nhân
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Thông tin cá nhân",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )

                    // Tuổi
                    Column {
                        Text(
                            "Tuổi",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666)
                        )
                        Text(
                            "${donor.age} tuổi",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Số điện thoại
                    Column {
                        Text(
                            "Số điện thoại",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666)
                        )
                        Text(
                            donor.phone,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Nhóm máu
                    Column {
                        Text(
                            "Nhóm máu",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666)
                        )
                        Text(
                            donor.bloodType,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Card thống kê hiến máu
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FA)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Thống kê hiến máu",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tổng số lần hiến
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tổng số lần hiến:")
                        Text(
                            "${donor.donationHistory.size} lần",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tổng lượng máu
                    val totalBlood = donor.donationHistory.sumOf { it.bloodAmount }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tổng lượng máu:")
                        Text(
                            "$totalBlood ml",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                    }

                    // Lần hiến gần nhất
                    if (donor.donationHistory.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        val lastDonation = donor.donationHistory.last()
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Lần cuối:")
                            Text(
                                lastDonation.date,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // CTA Buttons - CHỈ CÓ 1 BỘ NÚT
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onAddDonationClick(donorIndex) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Thêm lịch sử hiến máu", fontWeight = FontWeight.Medium)
                }

                // Nút Chỉnh sửa
                Button(
                    onClick = { onEditDonorClick(donorIndex) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Chỉnh sửa thông tin", fontWeight = FontWeight.Medium)
                }

                // Nút Xóa
                Button(
                    onClick = {
                        onDeleteDonorClick(donorIndex)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Text("Xóa hồ sơ", fontWeight = FontWeight.Medium)
                }
            }

            // Lịch sử hiến máu chi tiết (nếu có)
            if (donor.donationHistory.isNotEmpty()) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "Lịch sử hiến máu",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        donor.donationHistory.forEachIndexed { index, record ->
                            if (index > 0) {
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                            }

                            Column {
                                Text(
                                    "Lần ${index + 1}",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Ngày: ${record.date}")
                                Text("Địa điểm: ${record.location}")
                                Text("Lượng máu: ${record.bloodAmount} ml")
                            }
                        }
                    }
                }
            } else {
                // Hiển thị khi chưa có lịch sử hiến máu
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF5F5)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Chưa có lịch sử hiến máu",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666)
                        )
                        Text(
                            "Nhấn nút bên trên để thêm lịch sử hiến máu đầu tiên",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF888888)
                        )
                    }
                }
            }
        }
    }
}