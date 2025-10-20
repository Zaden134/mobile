package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToDonors: () -> Unit,
    onNavigateToEmployees: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToBloodRequests: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Hệ thống Quản lý Hiến máu",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome Section - ĐÃ CẬP NHẬT MÀU TƯƠI SÁNG
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Chào mừng đến với",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        "Hệ thống Quản lý Hiến máu",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Quản lý người hiến máu và nhân viên y tế",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Quick Stats - ĐÃ CẬP NHẬT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Người hiến máu",
                    count = "40+",
                    icon = Icons.Default.Favorite,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onNavigateToDonors,
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "Nhân viên",
                    count = "5+",
                    icon = Icons.Default.Group,
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = onNavigateToEmployees,
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "Yêu cầu máu",
                    count = "20+",
                    icon = Icons.Default.Warning,
                    color = MaterialTheme.colorScheme.tertiary,
                    onClick = onNavigateToBloodRequests,
                    modifier = Modifier.weight(1f)
                )
            }

            // Main Actions - ĐÃ CẬP NHẬT
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionCard(
                    title = "Quản lý Người hiến máu",
                    description = "Xem danh sách, đăng ký mới, quản lý thông tin người hiến máu",
                    icon = Icons.Default.Person,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onNavigateToDonors
                )

                ActionCard(
                    title = "Quản lý Nhân viên",
                    description = "Quản lý thông tin nhân viên y tế, phân công công việc",
                    icon = Icons.Default.Group,
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = onNavigateToEmployees
                )

                ActionCard(
                    title = "Đăng ký Hiến máu mới",
                    description = "Đăng ký thông tin người hiến máu mới",
                    icon = Icons.Default.Add,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onNavigateToSignUp
                )

                ActionCard(
                    title = "Quản lý Yêu cầu máu",
                    description = "Xem và xử lý các yêu cầu máu khẩn cấp từ bệnh viện",
                    icon = Icons.Default.Warning,
                    color = MaterialTheme.colorScheme.tertiary,
                    onClick = onNavigateToBloodRequests
                )
            }

            // Footer
            Text(
                "Hệ thống tích hợp Quản lý Hiến máu & Nhân viên",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    count: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                count,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            )
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = "Đi tới",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}