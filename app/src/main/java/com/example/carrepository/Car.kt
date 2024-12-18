package com.example.carrepository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "mark") val mark: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "color") val color: String
)