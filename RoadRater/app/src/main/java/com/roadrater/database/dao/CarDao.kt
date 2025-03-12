package com.roadrater.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.roadrater.database.entities.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert
    fun insertCar(car: CarEntity)

    @Query("DELETE FROM CarEntity WHERE numberPlate = :numberPlate")
    fun deleteCar(numberPlate: String)

    @Query("SELECT * FROM CarEntity")
    fun getAllCars(): Flow<List<CarEntity>>

    @Query("SELECT * FROM CarEntity WHERE numberPlate = :numberPlate LIMIT 1")
    fun getCarByNumberPlate(numberPlate: String): CarEntity
}
