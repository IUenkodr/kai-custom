package com.kaicustom.assistant

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaicustom.assistant.logic.Command
import com.kaicustom.designsystem.KaiTheme
import com.kaicustom.designsystem.LocalKaiColors
import com.kaicustom.designsystem.glassmorphic

class AssistantOverlayService : LifecycleService(), ViewModelStoreOwner {
    private lateinit var windowManager: WindowManager
    private var overlayView: ComposeView? = null
    private val store = ViewModelStore()

    override val viewModelStore: ViewModelStore get() = store

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        showOverlay()
    }

    private fun showOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) 
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY 
            else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        overlayView = ComposeView(this).apply {
            setContent {
                KaiTheme {
                    AssistantPaletteUI()
                }
            }
        }
        windowManager.addView(overlayView, params)
    }

    @Composable
    fun AssistantPaletteUI() {
        val viewModel: AssistantViewModel = viewModel()
        val query by viewModel.query.collectAsState()
        val commands by viewModel.filteredCommands.collectAsState()
        val result by viewModel.result.collectAsState()
        val colors = LocalKaiColors.current

        Box(
            modifier = Modifier
                .width(440.dp)
                .glassmorphic(borderRadius = 12)
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Kai Command Palette", 
                    color = colors.primary, 
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Precision Input Field
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, colors.outline, RoundedCornerShape(6.dp))
                        .background(colors.surface.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(12.dp)
                ) {
                    BasicTextField(
                        value = query,
                        onValueChange = { viewModel.onQueryChange(it) },
                        textStyle = androidx.compose.ui.text.TextStyle(color = colors.onSurface, fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (query.isEmpty()) {
                                Text("Search commands... (e.g. 'optimize')", color = colors.onSurfaceVariant, fontSize = 14.sp)
                            }
                            innerTextField()
                        }
                    )
                }

                // High-Density Command List
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .verticalScroll(androidx.compose.foundation.rememberScrollState())
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    commands.forEach { command ->
                        CommandRow(
                            command = command,
                            isActive = false, // Logic for highlighting selected item can be added
                            onClick = { viewModel.executeCommand(command, null, this@AssistantOverlayService) }
                        )
                    }
                }

                // Result Panel
                if (result != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.surfaceVariant, RoundedCornerShape(6.dp))
                            .padding(12.dp)
                    ) {
                        Text(text = result!!, color = colors.onSurfaceVariant, fontSize = 12.sp)
                    }
                }
            }
        }
    }

    @Composable
    fun CommandRow(command: Command, isActive: Boolean, onClick: () -> Unit) {
        val colors = LocalKaiColors.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isActive) colors.primary.copy(alpha = 0.2f) else Color.Transparent, RoundedCornerShape(6.dp))
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = command.icon, fontSize = 16.sp)
            Column {
                Text(text = command.label, color = colors.onSurface, fontSize = 14.sp)
                Text(text = command.description, color = colors.onSurfaceVariant, fontSize = 11.sp)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        overlayView?.let { windowManager.removeView(it) }
    }
}
