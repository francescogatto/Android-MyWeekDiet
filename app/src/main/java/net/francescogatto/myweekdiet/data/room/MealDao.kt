package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface MealDao {

    @Query(RoomContract.SELECT_MEALS_COUNT + " WHERE day_id = :idDay")
    fun getMealsTotalForDay(idDay : Long): Flowable<Int>

    @Insert
    fun insertAll(currencies: List<MealEntity>)

    @Query(RoomContract.SELECT_MEALS)
    fun getAllMeals(): Flowable<List<MealEntity>>

    @Query(RoomContract.SELECT_MEALS + " WHERE day_id = :dayId")
    fun getAllMealForDay(dayId : Long) : Flowable<List<MealEntity>>

}