package net.francescogatto.myweekdiet.di

import android.app.Application

class MyDietApplication : Application() {

  companion object {
    lateinit var appComponent: AppComponent
  }

  override fun onCreate() {
    super.onCreate()
    initializeDagger()
  }

  private fun initializeDagger() {
    appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .roomModule(RoomModule()).build()
  }
}

