package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import net.francescogatto.myweekdiet.R
import net.francescogatto.myweekdiet.domain.Day

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        val TAG = "MainFragment"
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
            daysList?.forEach {
                days.add(it.name)
            }
            homeRecyclerView.layoutManager = GridLayoutManager(activity, 2)
            homeRecyclerView.adapter = DaysAdapter(days) {
                //TODO show other fragment
            }
        })
    }


}