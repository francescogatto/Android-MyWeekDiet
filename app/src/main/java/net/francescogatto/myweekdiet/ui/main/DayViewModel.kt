package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.*
import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.francescogatto.myweekdiet.data.repository.MyDietRepository
import net.francescogatto.myweekdiet.di.MyDietApplication
import net.francescogatto.myweekdiet.domain.Day

class DayViewModel : BaseViewModel() {

    init {
        MyDietApplication.appComponent.inject(this)
    }

    private val liveDaysData: LiveData<List<Day>> get() =  dietRepository.getDaysList()

    fun loadDaysList(): LiveData<List<Day>>? {
        return liveDaysData
    }

    fun initLocalDays() {
        val disposable = dietRepository.getTotalDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isRoomDaysEmpty(it)) {
                        populate()
                    } else {
                        Log.i(MyDietRepository::class.java.simpleName, "DataSource has been already Populated")
                    }
                }
        compositeDisposable.add(disposable)
    }


    private fun isRoomDaysEmpty(daysTotal: Int) = daysTotal == 0

    private fun populate() {
        Completable.fromAction { dietRepository.addDays() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        Log.i(MyDietRepository::class.java.simpleName, "DataSource has been Populated")
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Log.e(MyDietRepository::class.java.simpleName, "DataSource hasn't been Populated", e)
                    }
                })
    }

}