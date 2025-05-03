package com.roadrater.database.entities

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val userId: String,
    val numberPlate: String,
    val review: Int,
    val comment: String,
    val createdAt: Int,
)
