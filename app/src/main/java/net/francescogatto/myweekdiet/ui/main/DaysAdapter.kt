package net.francescogatto.myweekdiet.ui.main

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.francescogatto.myweekdiet.R

class DaysAdapter(val items: ArrayList<String>, val listener: (String) -> Unit): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position, listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, pos: Int, listener: (String) -> Unit) = with(itemView) {
            findViewById<TextView>(R.id.textView).text = item
            val cvItem = findViewById<CardView>(R.id.card_view)
            cvItem.setOnClickListener {
                listener(item)
            }
        }
    }
}