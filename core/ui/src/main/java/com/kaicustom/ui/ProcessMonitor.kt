package com.kaicustom.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaicustom.designsystem.LocalKaiColors

data class ProcessInfo(val pid: String, val name: String, val cpu: String, val mem: String, val status: String)

@Composable
fun ProcessMonitorTab() {
    val colors = LocalKaiColors.current
    // In a real app, this would be a StateFlow from a ViewModel calling 'ps aux'
    val mockProcesses = remember {
        listOf(
            ProcessInfo("1204", "python3", "1.2%", "45MB", "RUNNING"),
            ProcessInfo("4502", "node", "0.5%", "120MB", "SLEEPING"),
            ProcessInfo("8910", "rust-bin", "4.8%", "12MB", "RUNNING"),
            ProcessInfo("1102", "sh", "0.1%", "4MB", "IDLE")
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Background Process Monitor", color = colors.onSurface, fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("PID", color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(1f))
            Text("Process", color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(2f))
            Text("CPU", color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(1f))
            Text("MEM", color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(1f))
            Text("Status", color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }

        // Process List
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            mockProcesses.forEach { proc ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.surfaceVariant, RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(proc.pid, color = colors.onSurface, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(proc.name, color = colors.primary, fontSize = 12.sp, modifier = Modifier.weight(2f))
                    Text(proc.cpu, color = colors.onSurface, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(proc.mem, color = colors.onSurface, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(proc.status, color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
