package com.example.carrepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CarRepository(private val carDao: CarDao) {
    val allCars: Flow<List<Car>> = carDao.getAllCars()

    suspend fun insertCar(car: Car) = withContext(Dispatchers.IO) {
        carDao.insertCar(car)
    }

    suspend fun deleteAllCars() = withContext(Dispatchers.IO) {
        carDao.deleteAllCars()
    }
}