package com.roadrater.database.repository

import kotlinx.coroutines.flow.Flow
import com.roadrater.database.RRDatabase
import com.roadrater.database.entities.CarEntity
import com.roadrater.domain.CarRepository


class CarRepositoryImpl(
    private val database: RRDatabase,
) : CarRepository {

    override suspend fun insert(carEntity: CarEntity) {
        database.carDao().insertCar(carEntity)
    }

    override fun deleteCar(numberPlate: String) {
        database.carDao().deleteCar(numberPlate)
    }

    override fun getAllCars(): Flow<List<CarEntity>> {
        return database.carDao().getAllCars()
    }

    override fun getCarByNumberPlate(numberPlate: String): CarEntity {
        return database.carDao().getCarByNumberPlate(numberPlate)
    }
}