package com.roadrater.ui.home.tabs

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DirectionsCarFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.roadrater.R
import com.roadrater.auth.GoogleAuthUiClient
import com.roadrater.presentation.util.Tab
import com.roadrater.ui.CarDetail
import io.github.jan.supabase.SupabaseClient
import org.koin.compose.koinInject

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable
        get() {
            val image = rememberVectorPainter(Icons.Outlined.DirectionsCarFilled)
            return TabOptions(
                index = 0u,
                title = stringResource(R.string.home_tab),
                icon = image,
            )
        }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val supabaseClient = koinInject<SupabaseClient>()
        val screenModel = rememberScreenModel { HomeTabScreenModel(supabaseClient) }
        val currentUser =
            GoogleAuthUiClient(context, Identity.getSignInClient(context)).getSignedInUser()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home") },
                    actions = {
                        AsyncImage(
                            model = currentUser?.profilePictureUrl,
                            contentDescription = "Profile picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .clickable { },
                        )
                    },
                )
            },
            floatingActionButton = {},
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                var expandedItem by remember { mutableStateOf<String?>(null) }
                var text by remember { mutableStateOf("") }
                var active by remember { mutableStateOf(false) }
                var items = remember {
                    mutableStateListOf(
                        "QGM3818",
                        "QCF292",
                        "MGH662",
                        "LLQ290",
                    )
                }
                val allSearchItems =
                    listOf("QGM3818", "QCF292", "MGH662", "LLQ290", "ABC123", "XYZ999")
                val searchResults = remember(text) {
                    if (text.isNotBlank()) {
                        allSearchItems.filter {
                            it.contains(text, ignoreCase = true)
                        }
                    } else {
                        emptyList()
                    }
                }

                val navigator = LocalNavigator.currentOrThrow

//                screenModel.cars.collectAsState()

                Scaffold {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = text,
                        onQueryChange = {
                            text = it
                        },
                        onSearch = {
                            if (text.isNotBlank() && !items.contains(text)) {
                                items.add(0, text)
                            }
                            active = false
                            text = ""
                        },
                        active = active,
                        onActiveChange = {
                            active = it
                        },
                        placeholder = {
                            Text(text = "Search")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        trailingIcon = {
                            if (active) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        if (text.isNotEmpty()) {
                                            text = ""
                                        } else {
                                            active = false
                                        }
                                    },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Icon",

                                    )
                            }
                        },

                        ) {
                        if (text.isBlank()) {
                            items.forEach {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { navigator.push(CarDetail("QCF292")) }
                                        .padding(all = 14.dp),
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(end = 10.dp),
                                        imageVector = Icons.Default.History,
                                        contentDescription = "History Icon",
                                    )
                                    Text(text = it)
                                }
                            }
                        } else {
                            if (searchResults.isEmpty()) {
                                Text(
                                    text = "No results found",
                                    modifier = Modifier.padding(14.dp),
                                )
                            } else {
                                searchResults.forEach {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { navigator.push(CarDetail("QCF292")) }
                                            .padding(14.dp),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.DirectionsCarFilled,
                                            contentDescription = "Car Icon",
                                            modifier = Modifier.padding(end = 10.dp),
                                        )
                                        Text(text = it)
                                    }
                                }
//
                            }
                        }
                    }
                }
            }
        }
    }
}
