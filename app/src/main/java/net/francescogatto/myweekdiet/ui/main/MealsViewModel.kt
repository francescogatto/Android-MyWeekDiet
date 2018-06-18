package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.*
import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.francescogatto.myweekdiet.data.repository.MyDietRepository
import net.francescogatto.myweekdiet.data.room.DayEntity
import net.francescogatto.myweekdiet.di.MyDietApplication
import net.francescogatto.myweekdiet.domain.Day
import net.francescogatto.myweekdiet.domain.Meal
import javax.inject.Inject

class MealsViewModel : ViewModel(),  LifecycleObserver {
    @Inject
    lateinit var dietRepository: MyDietRepository

    private val compositeDisposable = CompositeDisposable()
    private var liveDayData: LiveData<DayEntity>? = null
    private var liveMealsData: LiveData<List<Meal>>? = null

    fun loadMealsList(idDay: Long): LiveData<List<Meal>>? {
        if (liveMealsData == null) {
            liveMealsData = dietRepository.getAllMealsForDay(idDay)
        }
        return liveMealsData
    }

    init {
        initializeDagger()
    }

    private fun initializeDagger() = MyDietApplication.appComponent.inject(this)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in dietRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    fun getDay(descr: String): LiveData<DayEntity>{
        if (liveDayData == null) {
            liveDayData = dietRepository.getSingleDay(descr)
        }
        return liveDayData as LiveData<DayEntity>
    }



    fun initLocalMealsForDay(day: DayEntity) {
        val disposable = dietRepository.getTotalMealsForDay(day.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isRoomMealsEmpty(it)) {
                        populate(day.id)
                    } else {
                        Log.i(MyDietRepository::class.java.simpleName, "DataSource has been already Populated")
                    }
                }
        compositeDisposable.add(disposable)
    }

    private fun isRoomMealsEmpty(daysTotal: Int) = daysTotal == 0

    private fun populate(idDay : Long) {
        Completable.fromAction { dietRepository.addMeals(idDay) }
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

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }


}
