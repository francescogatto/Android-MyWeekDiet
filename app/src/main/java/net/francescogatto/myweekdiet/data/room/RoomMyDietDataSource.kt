package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(
        entities = arrayOf(DayEntity::class, MealEntity::class, FoodEntity::class),
        version = 1)
abstract class RoomMyDietDataSource : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun dayDao(): DayDao

    companion object {

        fun initDatabase(context: Context): RoomMyDietDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomMyDietDataSource::class.java,
                RoomContract.DATABASE_DIET
        ).build()

        fun getAllDays(): List<DayEntity> {
            val mutableDaysList = mutableListOf<DayEntity>()
            mutableDaysList.add(createDayEntity("Lunedì"))
            mutableDaysList.add(createDayEntity("Martedì"))
            mutableDaysList.add(createDayEntity("Mercoledì"))
            mutableDaysList.add(createDayEntity("Giovedì"))
            mutableDaysList.add(createDayEntity("Venerdì"))
            mutableDaysList.add(createDayEntity("Sabato"))
            mutableDaysList.add(createDayEntity("Domenica"))
            return mutableDaysList
        }

        private fun createDayEntity(name: String) = DayEntity(0,name)
    }
}