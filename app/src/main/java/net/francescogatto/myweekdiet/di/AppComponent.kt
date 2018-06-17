package net.francescogatto.myweekdiet.di

import dagger.Component
import net.francescogatto.myweekdiet.ui.main.DayViewModel
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, RoomModule::class))
@Singleton interface AppComponent {

  fun inject(dayViewModel: DayViewModel)
}
