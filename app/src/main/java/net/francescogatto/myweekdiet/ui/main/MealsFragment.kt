package net.francescogatto.myweekdiet.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.francescogatto.myweekdiet.R
import net.francescogatto.myweekdiet.domain.Meal

class MealsFragment : Fragment() {

    companion object {
        fun newInstance(descr : String) : MealsFragment {
            val fragment = MealsFragment()
            val args = Bundle()
            args.putInt("description", descr)
            fragment.setArguments(args)
            return fragment
            return MealsFragment()
        }
    }

    private lateinit var viewModel: MealsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.meals_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MealsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
