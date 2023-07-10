package com.example.movilapp1

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment  (val listener:(date: String)->Unit):DialogFragment(),DatePickerDialog.OnDateSetListener{
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val dayStr = if(day < 10) "0$day" else "$day"
        val monthStr = if(month < 9) "0${month + 1}" else "${month + 1}" // month es 0-indexed, por lo que le sumamos 1
        listener("$dayStr/$monthStr/$year")    // Aquí formateas la fecha a "dd/MM/yyyy"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val picker = DatePickerDialog(activity as Context, this, year, month, day)
        return picker
    }
}