package ru.apphabit.features.checkup.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.app.MainActivity
import ru.apphabit.features.checkup.data.CalendarData
import ru.apphabit.features.checkup.model.CalendarItem
import java.time.LocalDate


class CheckUpFragment : Fragment() {
//    private lateinit var vmCheckUp: CheckUpVM
//    private lateinit var checkUpAdapter: CheckUpAdapter
//    private lateinit var calendarAdapter: CalendarAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View = inflater.inflate(R.layout.all_habits, container, false)
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (activity as? MainActivity)?.setBottomNavVisibility(true)
//
//        val recyclerViewCalendar = view.findViewById<RecyclerView>(R.id.recycler_view_calendar)
//        val recyclerViewCheckUpHabits = view.findViewById<RecyclerView>(R.id.recycler_view_checkup_habits)
//        val currentDateTitle = view.findViewById<TextView>(R.id.current_date_title)
//
//        calendarAdapter = CalendarAdapter()
//        recyclerViewCalendar.apply {
//            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//            adapter = calendarAdapter
//        }
//
//        checkUpAdapter = CheckUpAdapter { checkUpId, isChecked ->
//
//            vmCheckUp.updateCheckUp(checkUpId)
//        }
//
//        recyclerViewCheckUpHabits.apply {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = checkUpAdapter
//        }
//
//        vmCheckUp = ViewModelProvider(this)[CheckUpVM::class.java]
//        vmCheckUp.checkups.observe(viewLifecycleOwner) { checkups ->
//            checkUpAdapter.submitList(checkups)
//        }
//
//        val checkups = vmCheckUp.checkups.value ?: emptyList()
//        val firstDate = checkups.minOfOrNull { it.dateCheckUp } ?: LocalDate.now()
//        val calendarData = CalendarData.createFromCheckUps(checkups.map { it.dateCheckUp })
//        val calendarDates = calendarData.generateCalendarDates()
//
//        val calendarItems = calendarDates.map { date ->
//            CalendarItem(
//                dayOfWeek = date.dayOfWeek.name.take(2),
//                date = date.toString()
//            )
//        }
//
//        calendarAdapter.updateItems(calendarItems)
//    }

    companion object {
        @JvmStatic
        fun newInstance() = CheckUpFragment()
    }
}