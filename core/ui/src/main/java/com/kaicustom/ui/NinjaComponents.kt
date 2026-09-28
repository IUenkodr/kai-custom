package com.kaicustom.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaicustom.designsystem.LocalKaiColors
import com.kaicustom.designsystem.glassmorphic

@Composable
fun NinjaCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = LocalKaiColors.current
    Box(
        modifier = modifier
            .background(colors.surface, RoundedCornerShape(8.dp))
            .border(1.dp, colors.outline, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        content()
    }
}

@Composable
fun NinjaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalKaiColors.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(colors.primary)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = colors.onPrimary, fontSize = 13.sp)
    }
}
