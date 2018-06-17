package net.francescogatto.myweekdiet.data.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomContract.TABLE_FOODS,
        foreignKeys = arrayOf(ForeignKey(entity = MealEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("meal_id"),
        onDelete = ForeignKey.CASCADE)))

data class FoodEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "meal_id")
        val mealId: Int,
        var type: String,
        var descr: String,
        var qty: String
)