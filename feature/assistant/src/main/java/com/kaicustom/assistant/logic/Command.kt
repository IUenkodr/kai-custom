package com.kaicustom.assistant.logic

import android.content.Context

interface Command {
    val id: String
    val label: String
    val description: String
    val icon: String
    
    // The actual execution logic
    suspend fun execute(context: Context, params: String? = null): CommandResult
}

data class CommandResult(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)

object CommandRegistry {
    private val commands = mutableMapOf<String, Command>()

    fun register(command: Command) {
        commands[command.id] = command
    }

    fun getAll(): List<Command> = commands.values.toList()

    fun findById(id: String): Command? = commands[id]

    fun search(query: String): List<Command> {
        return commands.values.filter { 
            it.label.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true) 
        }
    }
}
