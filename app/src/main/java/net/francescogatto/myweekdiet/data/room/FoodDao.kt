package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface FoodDao {

    @Query(RoomContract.SELECT_FOODS_COUNT)
    fun getFoodsTotal(): Flowable<Int>

    @Insert
    fun insertAll(currencies: List<FoodEntity>)

    @Query(RoomContract.SELECT_FOODS)
    fun getAllFoods(): Flowable<List<FoodEntity>>

    @Query(RoomContract.SELECT_FOODS +" WHERE meal_id = :mealId")
    fun getAllFoodsByMeal(mealId : Long): Flowable<List<FoodEntity>>

    @Insert
    fun insertFood(food: FoodEntity)

}