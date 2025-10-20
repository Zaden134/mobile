package com.example.blooddonationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.blooddonationapp.appscreen.navigation.AppNavigationWrapper
import com.example.blooddonationapp.ui.theme.BloodDonationAppTheme
import com.example.blooddonationapp.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloodDonationAppTheme {
                AppNavHost(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AppNavHost(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    AppNavigationWrapper(
        viewModel = viewModel,
        navController = navController
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BloodDonationAppTheme {
        // Để trống
    }
}