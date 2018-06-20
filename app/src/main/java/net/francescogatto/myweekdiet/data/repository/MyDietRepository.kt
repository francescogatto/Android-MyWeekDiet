package net.francescogatto.myweekdiet.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.francescogatto.myweekdiet.data.room.DayEntity
import net.francescogatto.myweekdiet.data.room.MealEntity
import net.francescogatto.myweekdiet.data.room.RoomMyDietDataSource
import net.francescogatto.myweekdiet.domain.Day
import net.francescogatto.myweekdiet.domain.Meal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyDietRepository @Inject constructor(private val roomMyDietDataSource: RoomMyDietDataSource) : Repository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    //region FOOD
    override fun getTotalFoods(): Flowable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFood() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //endregion

    //region DAY
    override fun addDays() {
        val daysEntityList = RoomMyDietDataSource.getAllDays()
        roomMyDietDataSource.dayDao().insertAll(daysEntityList)
    }

    override fun getTotalDays() : Flowable<Int> = roomMyDietDataSource.dayDao().getDaysTotal()

    override fun getSingleDay(description: String): LiveData<DayEntity> {
        val roomDayDao = roomMyDietDataSource.dayDao()
        val mutableLiveData = MutableLiveData<DayEntity>()
        val disposable = roomDayDao.getDay(description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    day -> mutableLiveData.value = day
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun getDaysList(): LiveData<List<Day>> {
        val roomDayDao = roomMyDietDataSource.dayDao()
        val mutableLiveData = MutableLiveData<List<Day>>()
        val disposable = roomDayDao.getAllDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dayList -> mutableLiveData.value = transform(dayList)
                }, {
                    t: Throwable? -> t?.printStackTrace()
                })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }
    //endregion

    //region MEAL
    override fun addMeals(idDay: Long) {
        val mealsEntityList = RoomMyDietDataSource.getAllMeals(idDay)
        roomMyDietDataSource.mealDao().insertAll(mealsEntityList)
    }

    override fun getTotalMealsForDay(idDay : Long) : Flowable<Int> = roomMyDietDataSource.mealDao().getMealsTotalForDay(idDay)


    override fun getAllMealsForDay(idDay : Long) : LiveData<List<Meal>> {
        val roomMealDao = roomMyDietDataSource.mealDao()
        val mutableLiveData = MutableLiveData<List<Meal>>()
        val disposable = roomMealDao.getAllMealForDay(idDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mealsList ->
                    mutableLiveData.value = transformMeal(mealsList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }


    override fun getMealsList(): LiveData<List<Meal>> {
        val roomDayDao = roomMyDietDataSource.mealDao()
        val mutableLiveData = MutableLiveData<List<Meal>>()
        val disposable = roomDayDao.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dayList ->
                    mutableLiveData.value = transformMeal(dayList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    //endregion


    private fun transform(currencies: List<DayEntity>): ArrayList<Day> {
        val dayList = ArrayList<Day>()
        currencies.forEach {
            dayList.add(Day(it.name))
        }
        return dayList
    }


    private fun transformMeal(currencies: List<MealEntity>): ArrayList<Meal> {
        val dayList = ArrayList<Meal>()
        currencies.forEach {
            dayList.add(Meal(it.name))
        }
        return dayList
    }

}