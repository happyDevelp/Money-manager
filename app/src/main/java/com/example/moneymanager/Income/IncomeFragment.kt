package com.example.moneymanager.Income

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.Utils.dataViewsAnimationToday
import com.example.moneymanager.Utils.dataViewsAnimationTwoDaysAgo
import com.example.moneymanager.Utils.dataViewsAnimationYesterday
import com.example.moneymanager.databinding.FragmentIncomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class IncomeFragment : Fragment() {
    private lateinit var db: DataBase
    private var calendar = Calendar.getInstance()
    lateinit var binding: FragmentIncomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataBase.getInstance(requireContext())

        binding.calendarPickerImage.setOnClickListener {
            showDatePicker()
        }
/*        lifecycleScope.launch {
            // test of working DB
            insertTransaction (TransactionEntity
                (0, "Income", "salary", 500, "Card", "12.03.24", "")
            )
        }*/


        CategoryContainterClickListener()
        setupCorrectDateOfDateViews()

    }


    private suspend fun insertTransaction(transaction: TransactionEntity)  {
        return withContext(Dispatchers.IO) {
            db.DAO.insertTransaction(transaction)
        }
    }

    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { datePicker, year: Int, monthOfYear: Int, dayOfYear: Int ->
                calendar.set(year, monthOfYear, dayOfYear)   // Set the selected date using the values received from the DatePicker dialog

                val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())// Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val formattedDate = dateFormat.format(calendar.time) // Formatting the selected date into a string

                    binding.apply {
                        date2daysago.text = formattedDate // Update the TextView to display the selected date with the "Selected Date: " prefix
                        textView2daysAgo.text = getString(R.string.textByDatePicker)

                        date2daysago.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.white
                        ))
                        textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.white
                        ))
                        binding.twoDaysAgoContainer.background =
                            (ContextCompat.getDrawable(requireContext(),
                                R.drawable.active_datepicker_background
                            ))

                        dateToday.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.black
                        ))
                        textViewToday.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.black
                        ))
                        todayContainer.background = (ContextCompat.getDrawable(requireContext(),
                            R.color.white
                        ))

                        dateYesterday.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.black
                        ))
                        textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.black
                        ))
                        yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(),
                            R.color.white
                        ))
                    }

                dataViewsAnimationTwoDaysAgo(binding)
            },

            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        calendar = Calendar.getInstance()
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis // set maxDate in DatePicker
        // Show the DatePicker dialog
        datePickerDialog.show()

    }

    private fun setupCorrectDateOfDateViews() {
        /** today **/
        // should to pass format of date (pattern) and region into SimpleDateFormat params. .format transform Date type to a string
        val correctTodayDateFormat =
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(calendar.time)
        binding.dateToday.text = correctTodayDateFormat

        /** yesterday **/
        // Need to check that the first day of the month has passed, otherwise we may get fake date
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        } else calendar.add(Calendar.DAY_OF_MONTH, -1)

        // should to pass format of date (pattern) and region into SimpleDateFormat params. .format transform Date type to a string
        val correctYesterdayDateFormat =
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(calendar.time)
        binding.dateYesterday.text = correctYesterdayDateFormat
        // need to update current date for future using
        calendar.add(Calendar.DAY_OF_MONTH, +1)


        /** the day before yesterday **/
        // Need to check that the second day of the month has passed, otherwise we may get fake date
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1 || calendar.get(Calendar.DAY_OF_MONTH) == 2 ) {
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

            if (calendar.get(Calendar.DAY_OF_MONTH) == 1) calendar.add(Calendar.DAY_OF_MONTH, -1) // if it is first day of the month
        } else calendar.add(Calendar.DAY_OF_MONTH, -2)

        // should to pass format of date (pattern) and region into SimpleDateFormat params. .format transform Date type to a string
        val correct2daysAgoDateFormat =
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(calendar.time)
        binding.date2daysago.text = correct2daysAgoDateFormat

        dateContainerClickListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dateContainerClickListener() {

        todaySelected()
        yesterdaySelected()
        twoDaysAgoSelected()
    }

    fun todaySelected() {binding.todayContainer.setOnClickListener {
        binding.apply {
            dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            it.background = (ContextCompat.getDrawable(requireContext(),
                R.drawable.active_datepicker_background
            ))

            dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.black
            ))
            yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(),
                R.color.white
            ))

            date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.black
            ))
            twoDaysAgoContainer.background = (ContextCompat.getDrawable(requireContext(),
                R.color.white
            ))
        }

        dataViewsAnimationToday(binding)
    }

    }

    private fun yesterdaySelected() {
        binding.yesterdayContainer.setOnClickListener {
            binding.apply {
                dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.white
                ))
                it.background = (ContextCompat.getDrawable(requireContext(),
                    R.drawable.active_datepicker_background
                ))

                dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                todayContainer.background = (ContextCompat.getDrawable(requireContext(),
                    R.color.white
                ))

                date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.black
                ))
                twoDaysAgoContainer.background = (ContextCompat.getDrawable(requireContext(),
                    R.color.white
                ))
            }

            dataViewsAnimationYesterday(binding)
        }
    }

    private fun twoDaysAgoSelected() {
        binding.twoDaysAgoContainer.setOnClickListener {
            binding.apply {
                date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.white
                ))
                it.background = (ContextCompat.getDrawable(requireContext(),
                    R.drawable.active_datepicker_background
                ))

                dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                todayContainer.background = (ContextCompat.getDrawable(requireContext(),
                    R.color.white
                ))

                dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.black
                ))
                yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(),
                    R.color.white
                ))
            }

            dataViewsAnimationTwoDaysAgo(binding)
        }
    }

    private fun CategoryContainterClickListener() {
        binding.salaryContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.category_green_active_background )
                helpContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.transparent_layout
                )
                giftContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.transparent_layout
                )
                otherContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.transparent_layout
                )

                salaryImv.background.alpha = 0
                helpImv.background.alpha = 255
                giftImv.background.alpha = 255
                otherImv.background.alpha = 255

                UtilManager.isSalaryClicked = true
                UtilManager.isHelpClicked = false
                UtilManager.isGiftClicked = false
            }
        }

        binding.helpContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                helpContainer.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.category_blue_active_background
                )
                giftContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                salaryImv.background.alpha = 255
                helpImv.background.alpha = 0
                giftImv.background.alpha = 255
                otherImv.background.alpha = 255

                UtilManager.isSalaryClicked = false
                UtilManager.isHelpClicked = true
                UtilManager.isGiftClicked = false
            }
        }

        binding.giftContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                helpContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                giftContainer.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.category_pink_active_background
                )
                otherContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                salaryImv.background.alpha = 255
                helpImv.background.alpha = 255
                giftImv.background.alpha = 0
                otherImv.background.alpha = 255

                UtilManager.isSalaryClicked = false
                UtilManager.isHelpClicked = false
                UtilManager.isGiftClicked = true
            }
        }

        binding.otherContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                helpContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                giftContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.category_beige_active_background
                )

                salaryImv.background.alpha = 255
                helpImv.background.alpha = 255
                giftImv.background.alpha = 255
                otherImv.background.alpha = 0

                UtilManager.isSalaryClicked = false
                UtilManager.isHelpClicked = false
                UtilManager.isGiftClicked = false
            }
        }
    }


}

object UtilManager {
    var isSalaryClicked: Boolean = false
    var isHelpClicked: Boolean = false
    var isGiftClicked: Boolean = false

    var comment: String = "s"
}