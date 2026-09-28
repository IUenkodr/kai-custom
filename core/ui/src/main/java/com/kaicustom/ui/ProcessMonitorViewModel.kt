package com.kaicustom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

data class ProcessInfo(val pid: String, val name: String, val cpu: String, val mem: String, val status: String)

class ProcessMonitorViewModel : ViewModel() {
    private val _processes = MutableStateFlow<List<ProcessInfo>>(emptyList())
    val processes: StateFlow<List<ProcessInfo>> = _processes

    init {
        // Refresh every 5 seconds
        viewModelScope.launch {
            while (true) {
                fetchProcesses()
                delay(5000L)
            }
        }
    }

    private fun fetchProcesses() {
        try {
            // Execute ps without paging, line-buffered, minimal output
            val proc = Runtime.getRuntime().exec(arrayOf("sh", "-c", "ps -eo pid,comm,%cpu,%mem,state --no-headers"))
            val reader = BufferedReader(InputStreamReader(proc.inputStream))
            val lines = mutableListOf<ProcessInfo>()
            reader.useLines { seq ->
                seq.forEach { line ->
                    // Expected format: PID COMMAND %CPU %MEM S
                    val parts = line.trim().split(Regex("\s+"))
                    if (parts.size >= 5) {
                        val pid = parts[0]
                        val name = parts[1]
                        val cpu = parts[2]
                        val mem = parts[3]
                        val status = parts[4]
                        lines.add(ProcessInfo(pid, name, cpu, mem, status))
                    }
                }
            }
            _processes.value = lines
        } catch (e: Exception) {
            // In case of failure, keep previous list
        }
    }
}
