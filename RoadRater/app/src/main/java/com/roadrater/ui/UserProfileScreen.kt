package com.roadrater.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.roadrater.database.entities.TableUser
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import org.koin.compose.koinInject

@Composable
fun UserProfileScreen(uid: String) {
    val supabaseClient = koinInject<SupabaseClient>()
    var user by remember { mutableStateOf<TableUser?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(uid) {
        loading = true
        error = null
        try {
            val users = supabaseClient.from("users")
                .select { filter { eq("uid", uid) } }
                .decodeList<TableUser>()
            user = users.firstOrNull()
            if (user == null) error = "User not found."
        } catch (e: Exception) {
            error = e.message
        } finally {
            loading = false
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = "User Profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            when {
                loading -> CircularProgressIndicator()
                error != null -> Text(text = error!!, color = MaterialTheme.colorScheme.error)
                user != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // If you have a profile picture URL, display it here
                    // AsyncImage(model = user!!.profilePictureUrl, ...)
                    Text(text = user!!.name ?: "No Name", style = MaterialTheme.typography.titleMedium)
                    Text(text = user!!.nickname ?: "No Nickname", style = MaterialTheme.typography.bodyMedium)
                    Text(text = user!!.email ?: "No Email", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

data class UserProfileScreenScreen(val uid: String) : Screen {
    @Composable
    override fun Content() {
        UserProfileScreen(uid)
    }
}
