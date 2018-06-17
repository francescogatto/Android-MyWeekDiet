package net.francescogatto.myweekdiet.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.francescogatto.myweekdiet.data.room.DayEntity
import net.francescogatto.myweekdiet.data.room.RoomMyDietDataSource
import net.francescogatto.myweekdiet.domain.Day
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyDietRepository @Inject constructor(private val roomMyDietDataSource: RoomMyDietDataSource) : Repository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()


    override fun getTotalFoods(): Flowable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFood() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDays() {
        val daysEntityList = RoomMyDietDataSource.getAllDays()
        roomMyDietDataSource.dayDao().insertAll(daysEntityList)
    }

    override fun getTotalDays() : Flowable<Int> = roomMyDietDataSource.dayDao().getDaysTotal()


    override fun getDaysList(): LiveData<List<Day>> {
        val roomDayDao = roomMyDietDataSource.dayDao()
        val mutableLiveData = MutableLiveData<List<Day>>()
        val disposable = roomDayDao.getAllDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dayList ->
                    mutableLiveData.value = transform(dayList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(currencies: List<DayEntity>): ArrayList<Day> {
        val dayList = ArrayList<Day>()
        currencies.forEach {
            dayList.add(Day(it.name))
        }
        return dayList
    }

}