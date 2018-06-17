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
import net.francescogatto.myweekdiet.di.MyDietApplication
import net.francescogatto.myweekdiet.domain.Day
import javax.inject.Inject

class DayViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var dietRepository: MyDietRepository

    private val compositeDisposable = CompositeDisposable()
    private var liveDaysData: LiveData<List<Day>>? = null

    init {
        initializeDagger()
    }

    private fun initializeDagger() = MyDietApplication.appComponent.inject(this)


    fun loadDaysList(): LiveData<List<Day>>? {
        if (liveDaysData == null) {
            liveDaysData = MutableLiveData<List<Day>>()
            liveDaysData = dietRepository.getDaysList()
        }
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

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in dietRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
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

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }

}