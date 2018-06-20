package net.francescogatto.myweekdiet.data.repository

import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import net.francescogatto.myweekdiet.data.room.DayEntity
import net.francescogatto.myweekdiet.data.room.FoodEntity
import net.francescogatto.myweekdiet.domain.Day
import net.francescogatto.myweekdiet.domain.Food
import net.francescogatto.myweekdiet.domain.Meal

interface Repository {

    fun getTotalFoods(): Flowable<Int>

    fun getTotalDays(): Flowable<Int>

    fun getTotalMealsForDay(idDay : Long): Flowable<Int>

    fun getSingleDay(description: String): LiveData<DayEntity>

    fun addFood(food: Food)

    fun getFoodsByMeal(mealId: Long) : LiveData<List<FoodEntity>>

    fun addDays()

    fun addMeals(idDay: Long)

    fun getMealsList(): LiveData<List<Meal>>

    fun getDaysList(): LiveData<List<Day>>

    fun getAllMealsForDay(dayId : Long) : LiveData<List<Meal>>


}