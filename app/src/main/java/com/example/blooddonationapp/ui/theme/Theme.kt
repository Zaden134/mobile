package com.example.blooddonationapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFE53935),          // Đỏ tươi nhẹ - màu chủ đạo cho máu
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFCDD2), // Đỏ nhạt cho container
    onPrimaryContainer = Color(0xFFB71C1C),

    secondary = Color(0xFF42A5F5),        // Xanh dương tươi sáng
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE3F2FD),
    onSecondaryContainer = Color(0xFF0D47A1),

    tertiary = Color(0xFF66BB6A),         // Xanh lá tươi sáng
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE8F5E8),
    onTertiaryContainer = Color(0xFF1B5E20),

    background = Color(0xFFF8F9FA),       // Nền trắng xám rất nhẹ
    onBackground = Color(0xFF212121),

    surface = Color(0xFFFFFFFF),          // Màu nền chính (trắng tinh)
    onSurface = Color(0xFF212121),

    surfaceVariant = Color(0xFFF5F5F5),   // Card, nền nhẹ
    onSurfaceVariant = Color(0xFF424242),

    outline = Color(0xFFE0E0E0),          // Viền xám nhẹ
    outlineVariant = Color(0xFFEEEEEE),

    error = Color(0xFFD32F2F),
    onError = Color.White,

    // Các màu bổ sung để tạo sự đồng bộ
    scrim = Color(0x99000000),
    inverseOnSurface = Color(0xFFF5F5F5),
    inverseSurface = Color(0xFF424242),
)

@Composable
fun BloodDonationAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}