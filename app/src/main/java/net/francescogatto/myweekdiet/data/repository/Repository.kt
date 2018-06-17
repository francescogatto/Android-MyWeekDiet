package net.francescogatto.myweekdiet.data.repository

import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import net.francescogatto.myweekdiet.domain.Day

interface Repository {

    fun getTotalFoods(): Flowable<Int>

    fun getTotalDays(): Flowable<Int>


    fun addFood()

    fun addDays()

    fun getDaysList(): LiveData<List<Day>>

}