package ru.apphabit.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ConvertStringToLocalDate {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalDate(dateString: String?): LocalDate? {
        return if (dateString.isNullOrEmpty()) {
            Log.e("ConvertStringToLocalDate", "Дата не передана или пуста.")
            null
        } else {
            try {
                LocalDate.parse(dateString, formatter)
            } catch (e: Exception) {
                Log.e("ConvertStringToLocalDate", "Ошибка парсинга даты: $dateString", e)
                null
            }
        }
    }
}
