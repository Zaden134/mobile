package com.example.blooddonationapp.appscreen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.blooddonationapp.appscreen.screens.*
import com.example.blooddonationapp.viewmodel.AppViewModel

sealed class Screen(val route: String) {
    // Donor Management
    object SignUp : Screen("signup")
    object DonationList : Screen("donation_list")
    object DonationDetail : Screen("donation_detail/{index}") {
        fun createRoute(index: Int) = "donation_detail/$index"
    }
    object DonationForm : Screen("donation_form")
    object AddDonationHistory : Screen("add_donation_history/{donorIndex}") {
        fun createRoute(donorIndex: Int) = "add_donation_history/$donorIndex"
    }
    object EditDonation : Screen("edit_donation/{index}") {
        fun createRoute(index: Int) = "edit_donation/$index"
    }

    // Employee Management
    object EmployeeList : Screen("employee_list")
    object EmployeeDetail : Screen("employee_detail/{id}") {
        fun createRoute(id: Int) = "employee_detail/$id"
    }
    object EmployeeEdit : Screen("employee_edit/{id}") {
        fun createRoute(id: String) = "employee_edit/$id"
    }

    // Blood Request Screen
    object BloodRequestList : Screen("blood_request_list")


    // Main Dashboard
    object Dashboard : Screen("dashboard")
}

fun NavGraphBuilder.appNavigation(
    viewModel: AppViewModel,
    navController: NavHostController,
    onOpenDrawer: () -> Unit
) {
    // Main Dashboard
    composable(Screen.Dashboard.route) {
        DashboardScreen(
            onNavigateToDonors = { navController.navigate(Screen.DonationList.route) },
            onNavigateToEmployees = { navController.navigate(Screen.EmployeeList.route) },
            onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
            onNavigateToBloodRequests = { navController.navigate(Screen.BloodRequestList.route) },
            onOpenDrawer = onOpenDrawer
        )
    }

    // Donor Management Screens
    composable(Screen.SignUp.route) {
        SignUpScreen(
            onSignUpSuccess = { donor ->
                viewModel.addDonor(donor)
                navController.navigate(Screen.DonationList.route) {
                    popUpTo(Screen.SignUp.route) { inclusive = true }
                }
            },
            onNavigateToList = {
                navController.navigate(Screen.DonationList.route)
            },
            onBackClick = {  // THÊM DÒNG NÀY
                navController.popBackStack()
            },
            onOpenDrawer = onOpenDrawer


        )
    }

    composable(Screen.DonationList.route) {
        DonationListScreen(
            donors = viewModel.filteredDonors,
            searchQuery = viewModel.donorSearchQuery,
            selectedFilter = viewModel.donorBloodFilter,
            onSearchQueryChange = { viewModel.donorSearchQuery = it },
            onFilterChange = { viewModel.donorBloodFilter = it },
            onDonorClick = { index ->
                navController.navigate(Screen.DonationDetail.createRoute(index))
            },
            onAddClick = {
                navController.navigate(Screen.DonationForm.route)
            },
            onNavigateToSignUp = {
                navController.navigate(Screen.SignUp.route)
            },
            onNavigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            onBackClick = {
                navController.popBackStack()
            },
            onOpenDrawer = onOpenDrawer

        )
    }

    // Donation Detail Screen
    composable(
        Screen.DonationDetail.route,
        arguments = listOf(navArgument("index") { type = NavType.IntType })
    ) { backStackEntry ->
        val index = backStackEntry.arguments?.getInt("index") ?: 0
        val donors = viewModel.donors
        if (index < donors.size) {
            DonationDetailScreen(
                donor = donors[index],
                donorIndex = index,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddDonationClick = { donorIndex ->
                    navController.navigate(Screen.AddDonationHistory.createRoute(donorIndex))
                },
                onEditDonorClick = { donorIndex ->
                    navController.navigate(Screen.EditDonation.createRoute(donorIndex))
                },
                onDeleteDonorClick = { donorIndex ->
                    viewModel.deleteDonor(donorIndex)
                    navController.popBackStack()
                }


            )
        }
    }

    // Edit Donor Screen
    composable(
        Screen.EditDonation.route,
        arguments = listOf(navArgument("index") { type = NavType.IntType })
    ) { backStackEntry ->
        val index = backStackEntry.arguments?.getInt("index") ?: 0
        val donors = viewModel.donors
        if (index < donors.size) {
            EditDonorScreen(
                donor = donors[index],
                onBackClick = {
                    navController.popBackStack()
                },
                onUpdateDonor = { updatedDonor ->
                    viewModel.updateDonor(index, updatedDonor)
                    navController.popBackStack()
                }


            )
        }
    }

    // Donation Form Screen
    composable(Screen.DonationForm.route) {
        DonationFormScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onSaveDonor = { newDonor ->
                viewModel.addDonor(newDonor)
                navController.popBackStack()
            }


        )
    }

    // Add Donation History Screen
    composable(
        Screen.AddDonationHistory.route,
        arguments = listOf(navArgument("donorIndex") { type = NavType.IntType })
    ) { backStackEntry ->
        val donorIndex = backStackEntry.arguments?.getInt("donorIndex") ?: 0
        val donors = viewModel.donors
        if (donorIndex < donors.size) {
            AddDonationHistoryScreen(
                donorName = donors[donorIndex].name,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveDonation = { donationRecord ->
                    viewModel.addDonationHistory(donorIndex, donationRecord)
                    navController.popBackStack()
                }


            )
        }
    }

    // Employee Management Screens
    composable(Screen.EmployeeList.route) {
        EmployeeListScreen(
            employees = viewModel.filteredEmployees,
            searchQuery = viewModel.employeeSearchQuery,
            roleFilter = viewModel.employeeRoleFilter,
            onSearchQueryChange = { viewModel.employeeSearchQuery = it },
            onRoleFilterChange = { viewModel.employeeRoleFilter = it },
            onEmployeeClick = { id ->
                navController.navigate(Screen.EmployeeDetail.createRoute(id))
            },
            onAddEmployeeClick = {
                navController.navigate(Screen.EmployeeEdit.createRoute("new"))
            },
            onNavigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            onBackClick = {
                navController.popBackStack()
            },
            onOpenDrawer = onOpenDrawer

        )
    }

    composable(
        Screen.EmployeeDetail.route,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        val employee = viewModel.getEmployeeById(id)
        if (employee != null) {
            EmployeeDetailScreen(
                employee = employee,
                onEdit = {
                    navController.navigate(Screen.EmployeeEdit.createRoute(id.toString()))
                },
                onDelete = {
                    viewModel.deleteEmployee(id)
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }


            )
        }
    }
    // TRONG appNavigation function - thêm composable mới
    composable(Screen.BloodRequestList.route) {
        BloodRequestListScreen(
            requests = viewModel.filteredBloodRequests,
            searchQuery = viewModel.bloodRequestSearchQuery,
            statusFilter = viewModel.bloodRequestStatusFilter,
            bloodFilter = viewModel.bloodRequestBloodFilter,
            urgencyFilter = viewModel.bloodRequestUrgencyFilter,
            onSearchQueryChange = { viewModel.bloodRequestSearchQuery = it },
            onStatusFilterChange = { viewModel.bloodRequestStatusFilter = it },
            onBloodFilterChange = { viewModel.bloodRequestBloodFilter = it },
            onUrgencyFilterChange = { viewModel.bloodRequestUrgencyFilter = it },
            onUpdateStatus = { requestId, newStatus ->
                viewModel.updateBloodRequestStatus(requestId, newStatus)
            },
            onNavigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            onBackClick = {
                navController.popBackStack()
            },
            onOpenDrawer = onOpenDrawer

        )
    }

    composable(
        Screen.EmployeeEdit.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val idArg = backStackEntry.arguments?.getString("id")
        val isNew = idArg == "new"
        val employee = if (!isNew) idArg?.toIntOrNull()?.let { viewModel.getEmployeeById(it) } else null

        EmployeeEditScreen(
            employee = employee,
            onSave = { name, role, dept, note, phone, email, isBloodDonor : Boolean, donorId: String ->
                if (isNew) {
                    viewModel.addEmployee(name, role, dept, note, phone, email, isBloodDonor = isBloodDonor,  donorId = donorId)
                } else {
                    employee?.let {
                        viewModel.updateEmployee(
                            it.copy(
                                name = name,
                                role = role,
                                department = dept,
                                note = note,
                                phone = phone,
                                email = email,
                                isBloodDonor = isBloodDonor,
                                donorId = if (isBloodDonor) donorId else null
                            )
                        )
                    }
                }
                navController.popBackStack()
            },
            onCancel = {
                navController.popBackStack()
            }

        )
    }
}