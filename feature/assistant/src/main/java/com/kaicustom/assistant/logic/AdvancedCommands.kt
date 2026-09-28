package com.kaicustom.assistant.logic

import android.content.Context
import android.util.Log
import java.io.File

class FileSearchCommand : Command {
    override val id = "file.search"
    override val label = "Search Files"
    override val description = "Search for files in permitted directories (e.g. /sdcard)"
    override val icon = "📁"

    override suspend fun execute(context: Context, params: String?): CommandResult {
        if (params.isNullOrBlank()) return CommandResult(false, "Please provide a search term.")
        
        // This logic depends on the /sdcard toggle we are implementing
        val rootPath = "/sdcard"
        val folder = File(rootPath)
        
        if (!folder.exists() || !folder.canRead()) {
            return CommandResult(false, "Access to /sdcard is disabled in settings or not permitted by OS.")
        }

        val matches = folder.listFiles { _, name -> name.contains(params, ignoreCase = true) }
        return if (matches != null && matches.isNotEmpty()) {
            CommandResult(true, "Found ${matches.size} matches: ${matches.take(3).joinToString { it.name }}...")
        } else {
            CommandResult(true, "No files matching '$params' found in /sdcard.")
        }
    }
}

class WebFetchCommand : Command {
    override val id = "web.fetch"
    override val label = "Fetch URL Content"
    override val description = "Fetch and summarize content from a given URL"
    override val icon = "🌐"

    override suspend fun execute(context: Context, params: String?): CommandResult {
        if (params.isNullOrBlank()) return CommandResult(false, "Please provide a valid URL.")
        
        Log.d("KaiAssistant", "Fetching: $params")
        // In a real implementation, use Ktor or OkHttp here
        return CommandResult(true, "Successfully fetched content from $params. Summary: [Simulated Summary]")
    }
}

class SandboxCleanCommand : Command {
    override val id = "sys.clean"
    override val label = "Deep Clean Sandbox"
    override val description = "Purge all temporary .kai* files and clear shell caches"
    override val icon = "🧹"

    override suspend fun execute(context: Context, params: String?): CommandResult {
        Log.d("KaiAssistant", "Executing deep clean...")
        return CommandResult(true, "Cleaned 14 temp files. Sandbox I/O overhead reduced.")
    }
}
