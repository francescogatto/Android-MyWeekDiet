package net.francescogatto.myweekdiet.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val myDietApplication: MyDietApplication) {

  @Provides @Singleton fun provideContext(): Context = myDietApplication

}
