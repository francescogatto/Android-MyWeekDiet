package net.francescogatto.myweekdiet.di

import dagger.Component
import net.francescogatto.myweekdiet.ui.main.DayViewModel
import net.francescogatto.myweekdiet.ui.main.FoodViewModel
import net.francescogatto.myweekdiet.ui.main.MealsViewModel
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, RoomModule::class))
@Singleton interface AppComponent {

  fun inject(dayViewModel: DayViewModel)
  fun inject(mealViewModel: MealsViewModel)
  fun inject(foodViewModel: FoodViewModel)

}
