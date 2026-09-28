package com.kaicustom.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaicustom.designsystem.LocalKaiColors

@Composable
fun ProcessMonitorTab() {
    val colors = LocalKaiColors.current
    val vm: ProcessMonitorViewModel = viewModel()
    val processList by vm.processes.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Background Process Monitor", color = colors.onSurface, fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))
        // Header row
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("PID", "Process", "CPU", "MEM", "Status").forEach { title ->
                Text(title, color = colors.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.weight(1f))
            }
        }
        // Process rows
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            processList.forEach { proc ->
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
