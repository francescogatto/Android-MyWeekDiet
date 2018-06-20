package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import net.francescogatto.myweekdiet.data.repository.MyDietRepository
import javax.inject.Inject

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var dietRepository: MyDietRepository

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in dietRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }


}