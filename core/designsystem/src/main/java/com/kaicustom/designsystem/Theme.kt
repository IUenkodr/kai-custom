package com.kaicustom.designsystem

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalKaiColors = staticCompositionLocalOf { LightKaiColors }

@Composable
fun KaiTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkKaiColors else LightKaiColors
    
    CompositionLocalProvider(LocalKaiColors provides colors) {
        MaterialTheme(
            // We still use MaterialTheme for the underlying engine, 
            // but we override the logic with our LocalKaiColors
            content = content
        )
    }
}
