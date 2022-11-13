package com.example.asteriod.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteriod.Asteroid
import com.example.asteriod.Constants
import com.example.asteriod.PictureOfDay
import com.example.asteriod.api.NetworkAsteriod
import com.example.asteriod.api.NetworkImageOfTheDay
import com.example.asteriod.api.domainAsDatabaseModel
import com.example.asteriod.api.parseAsteroidsJsonResult
import com.example.asteriod.database.AsteroidDatabase
import com.example.asteriod.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroidAll: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }
    val asteroidToday: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getTodayAsteroids(getTodayDate())) {
            it.asDomainModel()
        }
    val asteroid7DayAgo: LiveData<List<Asteroid>> =
        Transformations.map(
            database.asteroidDao.getLast7DaysAsteroids(
                getTodayDate(),
                getPast7Date()
            )
        ) {
            it.asDomainModel()
        }

    suspend fun refreshDataBase() {
        withContext(Dispatchers.IO) {
            val playlist = NetworkAsteriod.asteriod.getAsteriods().await()
            database.asteroidDao.insertAll(*domainAsDatabaseModel(parseAsteroidsJsonResult(
                JSONObject(playlist) )))
        }
    }
    suspend fun deleteOldAsteroid() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deleteOldAsteroid(getTodayDate())
        }
    }
    suspend fun getImageOfTheDay(): PictureOfDay {
        return NetworkImageOfTheDay.imageApi.getPhoto().await()
    }
}


fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime);
}

fun getPast7Date(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime);
}