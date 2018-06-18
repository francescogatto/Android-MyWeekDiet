package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import java.util.*

@Database(entities = arrayOf(DayEntity::class, MealEntity::class, FoodEntity::class), version = 1)
abstract class RoomMyDietDataSource : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun dayDao(): DayDao
    abstract fun mealDao(): MealDao

    companion object {

        fun initDatabase(context: Context): RoomMyDietDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomMyDietDataSource::class.java,
                RoomContract.DATABASE_DIET
        ).build()

        fun getAllDays(): List<DayEntity> {
            val mutableDaysList = mutableListOf<DayEntity>()
            mutableDaysList.add(DayEntity(1,"Lunedì"))
            mutableDaysList.add(DayEntity(2,"Martedì"))
            mutableDaysList.add(DayEntity(3,"Mercoledì"))
            mutableDaysList.add(DayEntity(4,"Giovedì"))
            mutableDaysList.add(DayEntity(5,"Venerdì"))
            mutableDaysList.add(DayEntity(6,"Sabato"))
            mutableDaysList.add(DayEntity(7,"Domenica"))
            return mutableDaysList
        }

        fun getAllMeals(idDay : Long): List<MealEntity> {
            val mutableDaysList = mutableListOf<MealEntity>()
            mutableDaysList.add(MealEntity(0,"Colazione",idDay))
            mutableDaysList.add(MealEntity(0,"Pranzo",idDay))
            mutableDaysList.add(MealEntity(0,"Cena",idDay))
            return mutableDaysList
        }



       // private fun createMealeEntity(name: String) = MealEntity(0,name)
    }
}