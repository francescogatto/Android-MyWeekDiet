package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.food_fragment.*
import net.francescogatto.myweekdiet.R
import net.francescogatto.myweekdiet.domain.Food

class FoodFragment : Fragment() {

    companion object {
        fun newInstance(id : Long) : FoodFragment {
            val fragment = FoodFragment()
            val args = Bundle()
            args.putLong("id", id)
            fragment.arguments= args
            return fragment
        }
    }

    private lateinit var viewModel: FoodViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.food_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)

        val mealId = arguments!!.getLong("id")
        viewModel.getFoodsByMeal(mealId).observe(this, Observer { foodEntities ->
            var foodList = ArrayList<String>()
            foodsRecyclerView.layoutManager = GridLayoutManager(activity, 2)
            foodEntities?.forEach { foodList.add(it.descr) }
            foodsRecyclerView.adapter = DaysAdapter(foodList) {

            }
        })
        fab.setOnClickListener { viewModel.addFood(Food("type", foodEditText.text.toString(), "qty",mealId)) }
    }

}
