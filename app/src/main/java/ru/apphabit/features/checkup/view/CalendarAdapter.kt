package ru.apphabit.features.checkup.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.features.checkup.model.CalendarItem
import ru.apphabit.R


class CalendarAdapter(
    private var items: List<CalendarItem> = listOf()
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<CalendarItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfWeekText: TextView = itemView.findViewById(R.id.text_day_of_week)
        val dateText: TextView = itemView.findViewById(R.id.text_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = items[position]
        holder.dayOfWeekText.text = item.dayOfWeek
        holder.dateText.text = item.date
    }

    override fun getItemCount(): Int = items.size
}
