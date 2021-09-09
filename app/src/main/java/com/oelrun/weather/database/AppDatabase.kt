package com.oelrun.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oelrun.weather.database.entity.City
import com.oelrun.weather.database.entity.CurrentWeather
import com.oelrun.weather.database.entity.DayWeather
import com.oelrun.weather.database.entity.HourWeather

@Database(
    entities = [
        CurrentWeather::class,
        DayWeather::class,
        HourWeather::class,
        City::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "forecast_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}