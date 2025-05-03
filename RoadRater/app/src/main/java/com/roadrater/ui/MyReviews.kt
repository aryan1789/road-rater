package com.roadrater.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roadrater.ui.theme.RoadRaterTheme
import com.roadrater.ui.home.tabs.HomeTabScreenModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCarFilled
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

object MyReviews : Tab {
    private fun readResolve(): Any = MyReviews

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

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        //val screenModel = rememberScreenModel { HomeTabScreenModel() }
        val currentUser = GoogleAuthUiClient(context, Identity.getSignInClient(context)).getSignedInUser()
        var selectedLabel by remember { mutableStateOf("All") }
        val labels = listOf("All","Speeding","Safe","Reckless")


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
//                Text("Home Tab")
                val reviews = listOf(
                    Review(
                        title = "Speeding on highway",
                        dateTime = "April 29, 2025 2:35 PM",
                        labels = listOf("Speeding"),
                        description = "Saw the driver weaving through traffic at high speed with no indicators"
                    ),
                    Review(
                        title = "Very polite driver",
                        dateTime = "April 30, 2025 9:12 AM",
                        labels = listOf("Safe"),
                        description = "Driver allowed me to merge and maintained safe distance throughout."
                    )
                )

                val filteredReviews = reviews.filter { review ->
                    selectedLabel == "All" || review.labels.contains(selectedLabel)
                }

                Text(
                    text = "My Reviews",
                    fontSize = 26.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )

                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    labels.forEach { label ->
                        Text(
                            text = label,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (label == selectedLabel) Color.Magenta else Color.Gray
                                )
                                .clickable { selectedLabel = label }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                LazyColumn {
                    items(filteredReviews) { review ->
                        ReviewCard(review)
                    }
                }
            }
        }
    }
}

    data class Review(
        val title: String,
        val dateTime: String,
        val labels: List<String>,
        val description: String
    )

    @Composable
    fun ReviewCard(review: Review) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(text = review.title, fontSize=20.sp,color = Color.LightGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.dateTime, fontSize = 14.sp, color = Color.LightGray)

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                review.labels.forEach { label ->
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0XFF6A6AFF))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(review.description,color = Color.White, fontSize = 16.sp)
        }
    }



