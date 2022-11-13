package com.example.asteriod.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteriod.Constants
import java.text.SimpleDateFormat
import java.util.*

@Dao
interface AsteroidDao {
    @Query("select * from asteroid ORDER BY close_approach_date ASC ")
    fun getAsteroids(): LiveData<List<AsteroidDbModel>>

    @Query("select * from asteroid WHERE close_approach_date > :today & close_approach_date < :nextWeek  ")
    fun getLast7DaysAsteroids(today: String,nextWeek : String): LiveData<List<AsteroidDbModel>>

    @Query("select * from asteroid WHERE close_approach_date = :today ")
    fun getTodayAsteroids(today: String): LiveData<List<AsteroidDbModel>>

    @Query("Delete from asteroid WHERE close_approach_date < :today ")
    fun deleteOldAsteroid(today: String): LiveData<List<AsteroidDbModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidDbModel)
}



@Database(entities = [AsteroidDbModel::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid_db"
            ).build()
        }
    }
    return INSTANCE
}
