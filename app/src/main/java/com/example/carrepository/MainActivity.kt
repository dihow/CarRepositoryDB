package com.example.carrepository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrepository.ui.theme.CarRepositoryTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = CarDatabase.getDatabase(this)
        val repository = CarRepository(database.carDao())
        setContent {
            CarRepositoryTheme {
                CarScreen(repository)
            }
        }
    }
}

@Composable
fun CarScreen(repository: CarRepository) {
    var mark by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val cars = remember { mutableStateListOf<Car>() }

    LaunchedEffect(Unit) {
        repository.allCars.collectLatest { cars.clear(); cars.addAll(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = mark, onValueChange = { mark = it }, label = { Text("Марка") })
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Год") })
        OutlinedTextField(value = color, onValueChange = { color = it }, label = { Text("Цвет") })

        Button(onClick = {
            scope.launch {
                repository.insertCar(Car(mark = mark, year = year.toInt(), color = color))
                mark = ""
                year = ""
                color = ""
            }
        }) {
            Text("Добавить автомобиль")
        }
        Button(onClick = {
            scope.launch {
                repository.deleteAllCars()
            }
        }) {
            Text("Удалить все записи")
        }

        CarTable(cars)
    }
}

@Composable
fun CarTable(cars: List<Car>) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            CarTitleRow()
        }
        items(cars) {
            CarRow(it)
        }
    }
}

@Composable
fun CarRow(car: Car) {
    Row(Modifier
        .background(Color.Gray)
        .fillMaxWidth()
        .padding(5.dp)) {
        Text(car.mark, modifier = Modifier.weight(.4f), fontSize = 22.sp)
        Text(car.year.toString(), modifier = Modifier.weight(.3f), fontSize = 22.sp)
        Text(car.color, modifier = Modifier.weight(.4f), fontSize = 22.sp)
    }
}

@Composable
fun CarTitleRow() {
    Row(Modifier
        .background(Color.Gray)
        .fillMaxWidth()
        .padding(5.dp)) {
        Text("Марка", color = Color.White, modifier = Modifier.weight(.4f), fontSize = 22.sp)
        Text("Год", color = Color.White, modifier = Modifier.weight(.3f), fontSize = 22.sp)
        Text("Цвет", color = Color.White, modifier = Modifier.weight(.4f), fontSize = 22.sp)
    }
}