package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.meals_fragment.*

import net.francescogatto.myweekdiet.R
import net.francescogatto.myweekdiet.data.room.DayEntity

class MealsFragment : Fragment() {

    companion object {
        fun newInstance(descr : String) : MealsFragment {
            val fragment = MealsFragment()
            val args = Bundle()
            args.putString("description", descr)
            fragment.setArguments(args)
            return fragment
        }
    }

    private lateinit var viewModel: MealsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.meals_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MealsViewModel::class.java)
        viewModel.let { lifecycle.addObserver(it) }
        //FIXME meglio passare l'id al fragment
        viewModel.getDay(arguments?.get("description").toString()).observe(this, Observer { dayEntity ->
            if (dayEntity != null) {
                viewModel.initLocalMealsForDay(dayEntity)
                loadData(dayEntity)
            }
        })
    }

    private fun loadData (dayEntity: DayEntity) {
        val days =  ArrayList<String>()
        viewModel.loadMealsList(dayEntity.id)?.observe(this, Observer { daysList ->
            daysList?.forEach {
                days.add(it.name)
            }
            mealsRecyclerView.layoutManager = GridLayoutManager(activity, 2)
            mealsRecyclerView.adapter = DaysAdapter(days) {
                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                        ?.replace(R.id.container, MealsFragment.newInstance(it))
                        ?.commit()
            }
        })
    }

}
