package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomContract.TABLE_MEALS,
        foreignKeys = arrayOf(ForeignKey(entity = DayEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("day_id"),
                onDelete = ForeignKey.CASCADE)))
data class MealEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var name: String,
        @ColumnInfo(name = "day_id")
        var dayId : Long

)