package com.roadrater.ui

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCarFilled
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class CarDetail(val plate: String): Screen {

    @Composable
    override fun Content() {

        val MakeModel = "Toyota Corolla"
        val reviews = remember {
            listOf(
                "Driver was courteous and followed all rules.",
                "Overtook dangerously near school zone.",
                "Very smooth driving and respectful.",
                "Cut me off on the motorway.",
            )
        }
        val navigator = LocalNavigator.currentOrThrow


        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Car Details")}
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.DirectionsCarFilled,
                    contentDescription = "Car Icon",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text=plate,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text=MakeModel,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top=4.dp,bottom=24.dp)
                )

                Text(
                    text="Reviews",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(reviews) { review ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Text(
                                text = review,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        //navigator.push(WriteReview(plate)) - To add when its done
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Write a Review")
                }//search bar on home screen, reviews
            }
        }
    }
}