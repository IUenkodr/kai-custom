package com.kaicustom.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaicustom.assistant.logic.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AssistantViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _filteredCommands = MutableStateFlow<List<Command>>(emptyList())
    val filteredCommands: StateFlow<List<Command>> = _filteredCommands

    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> = _result

    init {
        // Register Core Commands
        CommandRegistry.register(AnalyzeLogsCommand())
        CommandRegistry.register(OptimizeMemoryCommand())
        CommandRegistry.register(RebootSandboxCommand())
        
        // Register Advanced Commands
        CommandRegistry.register(FileSearchCommand())
        CommandRegistry.register(WebFetchCommand())
        CommandRegistry.register(SandboxCleanCommand())
        // Register Health Check
        CommandRegistry.register(SystemHealthCheckCommand())
        
        _filteredCommands.value = CommandRegistry.getAll()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        _filteredCommands.value = CommandRegistry.search(newQuery)
    }

    fun executeCommand(command: Command, params: String? = null, context: android.content.Context) {
        viewModelScope.launch {
            _result.value = "Executing ${command.label}..."
            val res = command.execute(context, params)
            _result.value = if (res.success) res.message else "Error: ${res.message}"
        }
    }

    fun clearResult() {
        _result.value = null
    }
}
