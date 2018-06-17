package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomContract.TABLE_DAYS)
data class DayEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var name: String
)