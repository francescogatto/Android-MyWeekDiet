package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_fragment.*
import net.francescogatto.myweekdiet.R

class DaysFragment : Fragment() {

    companion object {
        fun newInstance() = DaysFragment()
        val TAG = "DaysFragment"
    }

    private lateinit var viewModel: DayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        loadData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)
        viewModel.let { lifecycle.addObserver(it) }
        viewModel.initLocalDays()

    }

    private fun loadData () {
        val days =  ArrayList<String>()
        viewModel.loadDaysList()?.observe(this, Observer { daysList ->
            daysList?.forEach { days.add(it.name) }
            homeRecyclerView.layoutManager = GridLayoutManager(activity, 2)
            homeRecyclerView.adapter = DaysAdapter(days) {
                activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.addToBackStack(null)
                        ?.replace(R.id.container, MealsFragment.newInstance(it))
                        ?.commit()
            }
        })
    }


}
