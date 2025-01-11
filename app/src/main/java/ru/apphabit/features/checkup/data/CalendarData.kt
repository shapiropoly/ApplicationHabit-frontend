package ru.apphabit.features.checkup.data

import NUMBERS_OF_DAYS_FOR_CHECK_UP
import android.os.Build
import androidx.annotation.RequiresApi
import ru.apphabit.features.checkup.view.CheckUpVM
import java.time.LocalDate

data class CalendarData (
    val dateFirstCheckUp: LocalDate,
    val numberOfDays: Int = NUMBERS_OF_DAYS_FOR_CHECK_UP
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateCalendarDates(): List<LocalDate> {
        val daysList = mutableListOf<LocalDate>()
        var currentDate = dateFirstCheckUp

        repeat(numberOfDays) {
            daysList.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }
        return daysList
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun createFromCheckUps(checkUpDates: List<LocalDate>): CalendarData {
            val firstDate = checkUpDates.minOrNull() ?: LocalDate.now()
            return CalendarData(firstDate, NUMBERS_OF_DAYS_FOR_CHECK_UP)
        }
    }
}