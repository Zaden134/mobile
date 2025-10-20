package com.example.blooddonationapp.appscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.appscreen.navigation.Screen

@Composable
fun DrawerContent(
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
    ) {
        // Header với background sáng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    "Hệ thống Hiến máu",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Quản lý & Điều phối",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Cứu người - Sẻ chia",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Menu items với route chính xác từ Screen object
        val menuItems = listOf(
            Triple("🏠 Trang chủ", Screen.Dashboard.route, Icons.Default.Home),
            Triple("👥 Người hiến máu", Screen.DonationList.route, Icons.Default.Person),
            Triple("💼 Nhân viên", Screen.EmployeeList.route, Icons.Default.Group),
            Triple("🩸 Yêu cầu máu", Screen.BloodRequestList.route, Icons.Default.Warning),
            Triple("➕ Đăng ký hiến máu", Screen.SignUp.route, Icons.Default.Add)
        )

        menuItems.forEach { (label, route, icon) ->
            NavigationDrawerItem(
                label = {
                    Text(
                        label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                selected = false,
                onClick = {
                    onItemSelected(route)
                },
                icon = {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )

        // Footer item
        NavigationDrawerItem(
            label = {
                Text(
                    "⚙️ Cài đặt",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            selected = false,
            onClick = { onItemSelected(Screen.Dashboard.route) },
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Cài đặt",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}