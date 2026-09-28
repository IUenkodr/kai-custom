package com.kaicustom.designsystem

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.composed
import androidx.compose.runtime.Composable

/**
 * Glassmorphism Modifier for the Figma-inspired Professional UI.
 * Implements a semi-transparent surface with a blurred background 
 * and a high-contrast 1px border to maintain precision.
 */
fun Modifier.glassmorphic(
    borderRadius: Int = 12,
    borderAlpha: Float = 0.4f,
    surfaceAlpha: Float = 0.6f
) = composed {
    val colors = LocalKaiColors.current
    
    this.then(
        Modifier
            .clip(RoundedCornerShape(borderRadius.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colors.surface.copy(alpha = surfaceAlpha),
                        colors.surface.copy(alpha = surfaceAlpha * 0.8f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = colors.outline.copy(alpha = borderAlpha),
                shape = RoundedCornerShape(borderRadius.dp)
            )
            .blur(radius = 10.dp) // Background blur effect
    )
}
