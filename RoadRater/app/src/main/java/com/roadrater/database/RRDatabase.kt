package com.roadrater.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roadrater.database.entities.CarEntity
import com.roadrater.database.dao.CarDao

@Database(entities = [CarEntity::class], version = 1)
abstract class RRDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}
