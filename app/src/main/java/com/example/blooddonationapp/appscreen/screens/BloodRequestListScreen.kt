package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.BloodRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodRequestListScreen(
    requests: List<BloodRequest>,
    searchQuery: String,
    statusFilter: String,
    bloodFilter: String,
    onSearchQueryChange: (String) -> Unit,
    onStatusFilterChange: (String) -> Unit,
    onBloodFilterChange: (String) -> Unit,
    onUpdateStatus: (Int, String) -> Unit,
    onNavigateToDashboard: () -> Unit,
    onBackClick: () -> Unit,
    urgencyFilter: String,
    onUrgencyFilterChange: (String) -> Unit,
    onOpenDrawer: () -> Unit
) {
    var expandedStatus by remember { mutableStateOf(false) }
    var expandedBlood by remember { mutableStateOf(false) }
    var expandedUrgency by remember { mutableStateOf(false) }

    val statusOptions = listOf("Tất cả", "Pending", "Approved", "Rejected")
    val bloodOptions = listOf("Tất cả", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
    val urgencyOptions = listOf("Tất cả", "Low", "Medium", "High", "Critical")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🩸 Yêu cầu máu khẩn cấp",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Tìm kiếm bệnh nhân") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filter row - CẬP NHẬT MÀU SẮC
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Status filter
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expandedStatus = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Trạng thái: $statusFilter")
                    }
                    DropdownMenu(
                        expanded = expandedStatus,
                        onDismissRequest = { expandedStatus = false }
                    ) {
                        statusOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    onStatusFilterChange(option)
                                    expandedStatus = false
                                }
                            )
                        }
                    }
                }

                // Urgency filter
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expandedUrgency = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Mức độ: $urgencyFilter")
                    }
                    DropdownMenu(
                        expanded = expandedUrgency,
                        onDismissRequest = { expandedUrgency = false }
                    ) {
                        urgencyOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option,
                                        color = when (option) {
                                            "Critical" -> MaterialTheme.colorScheme.error
                                            "High" -> MaterialTheme.colorScheme.tertiary
                                            "Medium" -> Color(0xFFFFC107)
                                            "Low" -> Color(0xFF4CAF50)
                                            else -> MaterialTheme.colorScheme.onSurface
                                        }
                                    )
                                },
                                onClick = {
                                    onUrgencyFilterChange(option)
                                    expandedUrgency = false
                                }
                            )
                        }
                    }
                }

                // Blood type filter
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expandedBlood = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Nhóm máu: $bloodFilter")
                    }
                    DropdownMenu(
                        expanded = expandedBlood,
                        onDismissRequest = { expandedBlood = false }
                    ) {
                        bloodOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    onBloodFilterChange(option)
                                    expandedBlood = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Request list
            if (requests.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Không có yêu cầu máu phù hợp",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(requests, key = { it.id }) { request ->
                        BloodRequestCard(
                            request = request,
                            onUpdateStatus = onUpdateStatus
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BloodRequestCard(
    request: BloodRequest,
    onUpdateStatus: (Int, String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Alert icon for urgent requests
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Yêu cầu khẩn",
                    tint = when (request.urgency) {
                        "Critical" -> MaterialTheme.colorScheme.error
                        "High" -> MaterialTheme.colorScheme.tertiary
                        "Medium" -> Color(0xFFFFC107)
                        else -> Color(0xFF4CAF50)
                    },
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    request.patientName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Request details - CẬP NHẬT MÀU SẮC
            Column {
                Text("Nhóm máu: \uD83E\uDE78 ${request.bloodType}",
                    color = MaterialTheme.colorScheme.primary)
                Text("Địa điểm: \uD83D\uDCCD ${request.location}")
                Text("Cần: \uD83D\uDC89 ${request.requiredMl} ml",
                    color = MaterialTheme.colorScheme.tertiary)
                Text("Bệnh viện: \uD83C\uDFE5 ${request.hospital}")
                Text("SĐT: \uD83D\uDCDE ${request.contactPhone}")

                // Hiển thị mức độ khẩn cấp
                val urgencyColor = when (request.urgency) {
                    "Critical" -> MaterialTheme.colorScheme.error
                    "High" -> MaterialTheme.colorScheme.tertiary
                    "Medium" -> Color(0xFFFFC107)
                    else -> Color(0xFF4CAF50)
                }

                Text(
                    "🚨 Mức độ: ${request.urgency}",
                    color = urgencyColor,
                    fontWeight = FontWeight.SemiBold
                )

                if (request.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "📝 ${request.notes}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                val statusColor = when (request.status) {
                    "Pending" -> MaterialTheme.colorScheme.tertiary
                    "Approved" -> Color(0xFF388E3C)
                    "Rejected" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
                Text(
                    "Trạng thái: ${request.status}",
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action buttons - CẬP NHẬT MÀU SẮC
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onUpdateStatus(request.id, "Approved") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text("Phê duyệt")
                }

                Button(
                    onClick = { onUpdateStatus(request.id, "Rejected") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Từ chối")
                }

                Button(
                    onClick = { onUpdateStatus(request.id, "Pending") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Đặt lại")
                }
            }
        }
    }
}