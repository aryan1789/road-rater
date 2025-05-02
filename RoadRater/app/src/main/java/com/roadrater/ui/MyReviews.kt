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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roadrater.ui.theme.RoadRaterTheme
import androidx.compose.foundation.layout.*

class MyReviews:ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                RoadRaterTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    )
                    {
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
                        MyReviewsScreen(reviews = reviews)

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

    @Composable
    fun MyReviewsScreen(reviews: List<Review>) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
        ) {
            Text(
                text = "My Reviews",
                fontSize = 26.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(reviews) { review ->
                    ReviewCard(review)
                }
            }
        }
    }

