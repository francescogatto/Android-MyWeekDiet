package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface DayDao {

    @Query(RoomContract.SELECT_DAYS_COUNT)
    fun getDaysTotal(): Flowable<Int>

    @Insert
    fun insertAll(currencies: List<DayEntity>)

    @Query(RoomContract.SELECT_DAYS)
    fun getAllDays(): Flowable<List<DayEntity>>

    @Query(RoomContract.SELECT_DAYS + " WHERE name = :descrizione")
    fun getDay(descrizione: String) : Flowable<DayEntity>


}