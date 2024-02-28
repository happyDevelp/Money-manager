package com.example.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.databinding.FragmentAddingBinding
import com.example.moneymanager.databinding.FragmentIncomeBinding
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.util.Locale

class IncomeFragment : Fragment() {
    val calendar = Calendar.getInstance()
    lateinit var binding: FragmentIncomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarImage.setOnClickListener {
            showDatePicker()
        }



    }

    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { datePicker, year: Int, monthOfYear: Int, dayOfYear: Int ->
                val selectedDate = Calendar.getInstance() // Create a new Calendar instance to hold the selected date
                selectedDate.set(year, monthOfYear, dayOfYear)   // Set the selected date using the values received from the DatePicker dialog
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())// Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val formattedDate = dateFormat.format(selectedDate.time) // Format the selected date into a string
                binding.testTextview.text = formattedDate // Update the TextView to display the selected date with the "Selected Date: " prefix
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePicker dialog
        datePickerDialog.show()
    }
}