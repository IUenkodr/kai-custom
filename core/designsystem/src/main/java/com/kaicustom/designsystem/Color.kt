package com.kaicustom.designsystem

import androidx.compose.ui.graphics.Color

object FigmaPalette {
    // Neutrals - Slate/Zinc Scale
    val Slate50 = Color(0xFFF8FAFC)
    val Slate100 = Color(0xFFF1F5F9)
    val Slate200 = Color(0xFFE2E8F0)
    val Slate300 = Color(0xFFCBD5E1)
    val Slate400 = Color(0xFF94A3B8)
    val Slate500 = Color(0xFF64748B)
    val Slate600 = Color(0xFF475569)
    val Slate700 = Color(0xFF334155)
    val Slate800 = Color(0xFF1E293B)
    val Slate900 = Color(0xFF0F172A)
    val Slate950 = Color(0xFF020617)

    // Accents - Professional Precision
    val BrandPurple = Color(0xFF8B5CF6)
    val BrandBlue = Color(0xFF3B82F6)
    val BrandEmerald = Color(0xFF10B981)
    
    // Semantics
    val Error = Color(0xFFEF4444)
    val Warning = Color(0xFFF59E0B)
    val Success = Color(0xFF10B981)
}

data class KaiColors(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val outline: Color,
    val primary: Color,
    val onPrimary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color
)

val LightKaiColors = KaiColors(
    background = FigmaPalette.Slate50,
    surface = Color.White,
    surfaceVariant = FigmaPalette.Slate100,
    outline = FigmaPalette.Slate200,
    primary = FigmaPalette.BrandPurple,
    onPrimary = Color.White,
    onBackground = FigmaPalette.Slate900,
    onSurface = FigmaPalette.Slate900,
    onSurfaceVariant = FigmaPalette.Slate600
)

val DarkKaiColors = KaiColors(
    background = FigmaPalette.Slate950,
    surface = FigmaPalette.Slate900,
    surfaceVariant = FigmaPalette.Slate800,
    outline = FigmaPalette.Slate700,
    primary = FigmaPalette.BrandPurple,
    onPrimary = Color.White,
    onBackground = FigmaPalette.Slate50,
    onSurface = FigmaPalette.Slate50,
    onSurfaceVariant = FigmaPalette.Slate400
)
