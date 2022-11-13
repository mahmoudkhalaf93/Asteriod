package com.example.asteriod.main

import android.app.Application
import androidx.lifecycle.*
import com.example.asteriod.Asteroid
import com.example.asteriod.PictureOfDay
import com.example.asteriod.database.getDatabase
import com.example.asteriod.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)
    private val _haveAstroidToCheck = MutableLiveData<Asteroid?>()
    val haveAstroidToCheck : LiveData<Asteroid?> get() = _haveAstroidToCheck


    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay> get() = _imageOfDay
    val allAstroid = asteroidRepository.asteroidAll
    val last7WeekAstroid = asteroidRepository.asteroid7DayAgo
    val todayAstroid = asteroidRepository.asteroidToday

    init {
        viewModelScope.launch {
            _imageOfDay.value = asteroidRepository.getImageOfTheDay()
            asteroidRepository.refreshDataBase()
        }
    }

    fun setAstroidToDetailsScreen(asteroid: Asteroid) {
        _haveAstroidToCheck.value = asteroid
    }

    fun goToDetailsScreenDone() {
        _haveAstroidToCheck.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
