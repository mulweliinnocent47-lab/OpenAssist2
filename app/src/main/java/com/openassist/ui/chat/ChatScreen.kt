package com.openassist.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.openassist.ui.navigation.OpenAssistDestination
import com.openassist.ui.navigation.PremiumButton
import com.openassist.ui.navigation.PremiumCard
import com.openassist.ui.navigation.PremiumPage
import com.openassist.ui.navigation.PremiumPill
import com.openassist.ui.navigation.premiumMutedTextColor
import com.openassist.ui.navigation.premiumTextColor
import com.openassist.ux.ExperienceCatalog
import com.openassist.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    onSettings: () -> Unit,
    onModels: () -> Unit,
    onPermissions: () -> Unit,
    onToolApproval: () -> Unit,
    onMcpServers: () -> Unit,
    onHistory: () -> Unit,
    onAbout: () -> Unit,
    onScreenIntelligence: () -> Unit,
    onAutomationCenter: () -> Unit,
    onVoiceOverlay: () -> Unit,
    onVoiceStudio: () -> Unit,
    onWorkspace: () -> Unit,
    onImageStudio: () -> Unit,
    onCodeCanvas: () -> Unit,
    onMcpMarketplace: () -> Unit,
    onMcpConnections: () -> Unit,
    onConnectionsHub: () -> Unit,
    onDesktopBridge: () -> Unit,
    onAgentEngine: () -> Unit,
    onKnowledgeBase: () -> Unit,
    onUniversalSearch: () -> Unit,
    onCommandPalette: () -> Unit,
    onAgentTaskCenter: () -> Unit,
    onContextSidebar: () -> Unit,
) {
    val state by chatViewModel.state.collectAsState()
    var input by remember { mutableStateOf("") }
    val navigate: (OpenAssistDestination) -> Unit = {
        when (it) {
            OpenAssistDestination.Chat -> Unit
            OpenAssistDestination.ConversationHistory -> onHistory()
            OpenAssistDestination.Workspace -> onWorkspace()
            OpenAssistDestination.KnowledgeBase -> onKnowledgeBase()
            OpenAssistDestination.ConnectionsHub -> onConnectionsHub()
            OpenAssistDestination.Settings -> onSettings()
            else -> Unit
        }
    }

    PremiumPage("OpenAssist\nHome", "Your premium AI operating system: chat, voice, agents, quick actions, activity, and active model in one place.", OpenAssistDestination.Chat, navigate, action = { PremiumPill("Search", onClick = onUniversalSearch) }) {
        LazyColumn(Modifier.weight(1f)) {
            item {
                PremiumCard(Modifier.fillMaxWidth(0.58f)) {
                    Text("Assistant", color = premiumMutedTextColor(), fontWeight = FontWeight.Bold)
                    Text("I can plan tasks, generate files, answer from Knowledge, control tools safely, and keep every major feature within 1–2 taps.", color = premiumTextColor())
                }
                Spacer(Modifier.height(12.dp))
                PremiumCard(Modifier.fillMaxWidth(0.72f).padding(start = 90.dp), selected = true) {
                    Text("You", color = premiumMutedTextColor(), fontWeight = FontWeight.Bold)
                    Text("Make this app feel premium and ready for production.", color = premiumTextColor())
                }
                Spacer(Modifier.height(12.dp))
                PremiumCard(Modifier.fillMaxWidth(0.72f).padding(start = 150.dp)) {
                    Text("Tool result", color = premiumMutedTextColor(), fontWeight = FontWeight.Bold)
                    Text("OpenAssist mockups updated successfully.", color = premiumTextColor())
                }
                Spacer(Modifier.height(12.dp))
            }
            items(state.messages) { message ->
                PremiumCard(Modifier.padding(vertical = 4.dp)) {
                    Text(message.role, color = premiumMutedTextColor(), fontWeight = FontWeight.Bold)
                    Text(message.content, color = premiumTextColor())
                }
            }
        }
        PremiumCard(selected = true) {
            Text("Universal Search", color = premiumTextColor(), fontWeight = FontWeight.ExtraBold)
            Text("Search chats, files, projects, knowledge, models, MCP servers, settings, and commands.", color = premiumMutedTextColor())
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                PremiumButton("Search Everything") { onUniversalSearch() }
                Spacer(Modifier.width(12.dp))
                PremiumButton("« Commands »") { onCommandPalette() }
            }
        }
        Spacer(Modifier.height(12.dp))
        PremiumCard {
            Text("Quick Actions", color = premiumTextColor(), fontWeight = FontWeight.ExtraBold)
            ExperienceCatalog.homeQuickActions.take(8).forEach { action ->
                Text("• ${action.title} — ${action.description}", color = premiumMutedTextColor())
            }
        }
        Spacer(Modifier.height(12.dp))
        PremiumCard {
            Text("Recent Activity", color = premiumTextColor(), fontWeight = FontWeight.ExtraBold)
            ExperienceCatalog.recentActivity.forEach { activity -> Text("• $activity", color = premiumMutedTextColor()) }
        }
        Spacer(Modifier.height(12.dp))
        state.pendingToolSummary?.let { summary ->
            PremiumCard(selected = true) {
                Text("Confirmation required", color = premiumTextColor(), fontWeight = FontWeight.ExtraBold)
                Text(summary, color = premiumMutedTextColor())
                Spacer(Modifier.height(12.dp))
                Row {
                    PremiumButton("Approve") { chatViewModel.confirmPendingAction() }
                    Spacer(Modifier.width(12.dp))
                    PremiumButton("Cancel") { chatViewModel.cancelPendingAction() }
                }
            }
        }
        PremiumCard {
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(input, { input = it }, Modifier.weight(1f), placeholder = { Text("Type a message...") })
                Spacer(Modifier.width(12.dp))
                PremiumButton("Send") { chatViewModel.send(input); input = "" }
            }
        }
        Row(Modifier.fillMaxWidth()) {
            PremiumPill("Permissions", onClick = onPermissions)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Tool Approval", onClick = onToolApproval)
            Spacer(Modifier.width(8.dp))
            PremiumPill("About", onClick = onAbout)
        }
        Row(Modifier.fillMaxWidth()) {
            PremiumPill("Screen Intelligence", onClick = onScreenIntelligence)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Automation", onClick = onAutomationCenter)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Voice Overlay", onClick = onVoiceOverlay)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Voice Studio", onClick = onVoiceStudio)
        }
        Row(Modifier.fillMaxWidth()) {
            PremiumPill("Agent Tasks", onClick = onAgentTaskCenter)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Context", onClick = onContextSidebar)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Commands", onClick = onCommandPalette)
        }
        Row(Modifier.fillMaxWidth()) {
            PremiumPill("Workspace", onClick = onWorkspace)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Image Studio", onClick = onImageStudio)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Code Canvas", onClick = onCodeCanvas)
        }
        Row(Modifier.fillMaxWidth()) {
            PremiumPill("Agent Engine", onClick = onAgentEngine)
            Spacer(Modifier.width(8.dp))
            PremiumPill("My Knowledge", onClick = onKnowledgeBase)
        }
        Row(Modifier.fillMaxWidth()) {
            PremiumPill("MCP Marketplace", onClick = onMcpMarketplace)
            Spacer(Modifier.width(8.dp))
            PremiumPill("MCP Connections", onClick = onMcpConnections)
            Spacer(Modifier.width(8.dp))
            PremiumPill("Desktop Bridge", onClick = onDesktopBridge)
        }
    }
}
