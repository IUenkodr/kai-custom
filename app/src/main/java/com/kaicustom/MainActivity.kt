package com.kaicustom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaicustom.designsystem.KaiTheme
import com.kaicustom.designsystem.LocalKaiColors
import com.kaicustom.ui.NinjaCard
import com.kaicustom.ui.NinjaButton
import com.kaicustom.ui.ProcessMonitorTab
import com.kaicustom.ui.settings.AppSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaiTheme(darkTheme = true) {
                MainCanvas()
            }
        }
    }
}

@Composable
fun MainCanvas() {
    val colors = LocalKaiColors.current
    var currentTab by remember { mutableStateOf("Dashboard") }
    var sdCardEnabled by remember { mutableStateOf(AppSettings.isSdCardAccessEnabled(context = LocalContext.current)) }

    // Synchronize UI toggle back to prefs
    LaunchedEffect(sdCardEnabled) {
        AppSettings.setSdCardAccess(context = LocalContext.current, enabled = sdCardEnabled)
    }

    Row(modifier = Modifier.fillMaxSize().background(colors.background)) {
        // Sidebar navigation
        Column(
            modifier = Modifier
                .width(240.dp)
                .fillMaxHeight()
                .background(colors.surface)
                .border(1.dp, colors.outline),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "KAI CUSTOM",
                modifier = Modifier.padding(20.dp),
                color = colors.primary,
                fontSize = 14.sp
            )
            SidebarItem("Dashboard", active = (currentTab == "Dashboard")) { currentTab = "Dashboard" }
            SidebarItem("Monitor", active = (currentTab == "Monitor")) { currentTab = "Monitor" }
            SidebarItem("Assistant", active = (currentTab == "Assistant")) { currentTab = "Assistant" }
            SidebarItem("Settings", active = (currentTab == "Settings")) { currentTab = "Settings" }
        }
        
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentTab) {
                "Dashboard" -> DashboardView(colors, onSdCardChange = { sdCardEnabled = it })
                "Monitor" -> ProcessMonitorTab()
                "Assistant" -> Text("Assistant Placeholder", color = colors.onSurface, modifier = Modifier.align(Alignment.Center))
                "Settings" -> SettingsView(colors, sdCardEnabled, onToggle = { sdCardEnabled = it })
            }
        }
    }
}

@Composable
fun DashboardView(colors: androidx.compose.ui.graphics.Color, onSdCardChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Workspace / System Overview",
            color = colors.onSurface,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            NinjaCard(modifier = Modifier.weight(1f)) { Text("CPU Load: 12%", color = colors.onSurface) }
            NinjaCard(modifier = Modifier.weight(1f)) { Text("Memory: 4.2GB / 16GB", color = colors.onSurface) }
            NinjaCard(modifier = Modifier.weight(1f)) { Text("Status: Operational", color = colors.onSurface) }
        }
        Spacer(modifier = Modifier.height(24.dp))
        NinjaCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("External Storage Access", color = colors.onSurface, fontSize = 14.sp)
                    Text("Allow Assistant to scan /sdcard", color = colors.onSurfaceVariant, fontSize = 11.sp)
                }
                Switch(checked = sdCardEnabled, onCheckedChange = onSdCardChange)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        NinjaCard(modifier = Modifier.fillMaxWidth().height(300.dp)) { Text("System Telemetry Log", color = colors.onSurface, fontSize = 14.sp) }
        Spacer(modifier = Modifier.height(24.dp))
        NinjaButton(text = "Launch Assistant", onClick = { /* Trigger overlay */ })
    }
}

@Composable
fun SettingsView(colors: androidx.compose.ui.graphics.Color, sdCardEnabled: Boolean, onToggle: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("App Settings", color = colors.onSurface, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        NinjaCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Enable /sdcard Access", color = colors.onSurface, fontSize = 14.sp)
                    Text("Assistant can read/write inside /sdcard when on.", color = colors.onSurfaceVariant, fontSize = 11.sp)
                }
                Switch(checked = sdCardEnabled, onCheckedChange = onToggle)
            }
        }
    }
}

@Composable
fun SidebarItem(label: String, active: Boolean, onClick: () -> Unit) {
    val colors = LocalKaiColors.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .background(if (active) colors.surfaceVariant else Color.Transparent, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(label, color = if (active) colors.primary else colors.onSurfaceVariant, fontSize = 13.sp)
    }
}
