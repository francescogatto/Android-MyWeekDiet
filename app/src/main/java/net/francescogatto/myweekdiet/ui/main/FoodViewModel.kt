package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.francescogatto.myweekdiet.data.repository.MyDietRepository
import net.francescogatto.myweekdiet.data.room.FoodEntity
import net.francescogatto.myweekdiet.di.MyDietApplication
import net.francescogatto.myweekdiet.domain.Food

class FoodViewModel : BaseViewModel() {

    init {
        MyDietApplication.appComponent.inject(this)
    }

    fun addFood(food: Food) {
        Completable.fromAction { dietRepository.addFood(food) }
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

    fun getFoodsByMeal(mealId : Long) : LiveData<List<FoodEntity>>{
        return dietRepository.getFoodsByMeal(mealId)
    }

}