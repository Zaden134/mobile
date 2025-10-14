package com.example.blooddonationapp.appscreen.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.blooddonationapp.appscreen.components.DrawerContent
import com.example.blooddonationapp.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigationWrapper(
    viewModel: AppViewModel,
    navController: NavHostController
) {
    val drawerState = remember { androidx.compose.material3.DrawerState(androidx.compose.material3.DrawerValue.Closed) }
    val scope = rememberCoroutineScope()

    // Hàm mở drawer
    val onOpenDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }

    // Hàm đóng drawer và chuyển trang
    val onItemSelected: (String) -> Unit = { route ->
        scope.launch {
            drawerState.close()
        }
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true
                // Xóa stack để tránh tích lũy màn hình
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                // Khôi phục state khi quay lại cùng destination
                restoreState = true
            }
        }
    }

    androidx.compose.material3.ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemSelected = onItemSelected,
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route
        ) {
            appNavigation(
                viewModel = viewModel,
                navController = navController,
                onOpenDrawer = onOpenDrawer
            )
        }
    }
}