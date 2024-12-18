package com.example.carrepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert
    suspend fun insertCar(car: Car)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<Car>>

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()
}