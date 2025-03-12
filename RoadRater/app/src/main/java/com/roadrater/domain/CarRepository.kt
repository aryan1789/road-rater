package com.roadrater.domain

import com.roadrater.database.entities.CarEntity
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    suspend fun insert(productsEntity: CarEntity)

    fun deleteCar(numberPlate: String)

    fun getAllCars(): Flow<List<CarEntity>>

    fun getCarByNumberPlate(numberPlate: String): CarEntity
}
