package com.example.blooddonationapp.appscreen.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blooddonationapp.model.Donor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationListScreen(
    donors: List<Donor>,
    searchQuery: String,
    selectedFilter: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterChange: (String) -> Unit,
    onDonorClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onBackClick: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    val bloodTypes = listOf("Tất cả", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    // Danh sách sau khi lọc
    val filteredList = donors.filter { donor ->
        val query = searchQuery.trim().lowercase()
        val donorName = donor.name.lowercase()

        val matchesName = when {
            query.isBlank() -> true
            else -> {
                val nameWords = donorName.split("\\s+".toRegex())
                val queryWords = query.split("\\s+".toRegex())
                queryWords.all { qw ->
                    nameWords.any { it.startsWith(qw) }
                }
            }
        }

        val matchBlood = selectedFilter == "Tất cả" || donor.bloodType == selectedFilter
        matchesName && matchBlood
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách người hiến máu") },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm người hiến máu")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {
            // Ô tìm kiếm
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Tìm kiếm theo tên") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // Filter nhóm máu - ĐÃ CẬP NHẬT
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(bloodTypes.size) { i ->
                    FilterChip(
                        selected = selectedFilter == bloodTypes[i],
                        onClick = { onFilterChange(bloodTypes[i]) },
                        label = { Text(bloodTypes[i]) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Không tìm thấy người hiến máu phù hợp", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = onNavigateToSignUp,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Đăng ký người hiến máu mới")
                        }
                    }
                }
            } else {
                LazyColumn {
                    itemsIndexed(filteredList) { filteredIndex, donor ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            onClick = {
                                val actualIndex = donors.indexOf(donor)
                                if (actualIndex != -1) {
                                    onDonorClick(actualIndex)
                                }
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Avatar ký tự đầu
                                Surface(
                                    shape = MaterialTheme.shapes.large,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = donor.name.first().toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }

                                // Thông tin
                                Column(Modifier.weight(1f)) {
                                    Text(donor.name, style = MaterialTheme.typography.titleMedium)
                                    Text("Tuổi: ${donor.age}", style = MaterialTheme.typography.bodySmall)
                                    Text("SĐT: ${donor.phone}", style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        "Số lần hiến: ${donor.donationHistory.size}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (donor.donationHistory.size > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // Nhóm máu
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = MaterialTheme.colorScheme.primary,
                                ) {
                                    Text(
                                        text = donor.bloodType,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}