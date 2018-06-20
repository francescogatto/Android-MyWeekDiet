package net.francescogatto.myweekdiet.data.room

class RoomContract {

    companion object {

        const val DATABASE_DIET = "diet.db"

        const val TABLE_DAYS = "days"
        const val TABLE_MEALS = "meals"
        const val TABLE_FOODS = "foods"


        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_FOODS_COUNT = SELECT_COUNT + TABLE_FOODS
        const val SELECT_FOODS = SELECT_FROM + TABLE_FOODS

        const val SELECT_DAYS_COUNT = SELECT_COUNT + TABLE_DAYS
        const val SELECT_DAYS = SELECT_FROM + TABLE_DAYS

        const val SELECT_MEALS_COUNT = SELECT_COUNT + TABLE_MEALS
        const val SELECT_MEALS = SELECT_FROM + TABLE_MEALS

    }
}