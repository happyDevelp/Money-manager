package com.example.moneymanager.Adding

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.Income.IncomeFragment
import com.example.moneymanager.Income.UtilManager
import com.example.moneymanager.R
import com.example.moneymanager.Spending.SpendingFragment
import com.example.moneymanager.Utils.coinAnimation1
import com.example.moneymanager.Utils.coinAnimation2
import com.example.moneymanager.Utils.coinAnimation3
import com.example.moneymanager.Utils.coinAnimationDown
import com.example.moneymanager.Utils.coinAnimationUp
import com.example.moneymanager.Utils.dataViewsAnimationToday
import com.example.moneymanager.Utils.dataViewsAnimationTwoDaysAgo
import com.example.moneymanager.Utils.dataViewsAnimationYesterday
import com.example.moneymanager.databinding.FragmentAddingBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddingFragment : Fragment() {
    private lateinit var binding: FragmentAddingBinding
    private lateinit var viewModel: AddingViewModel
    private var calendar = android.icu.util.Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddingViewModel::class.java)
        //disable user swiping between fragments in viewPager2
        binding.viewPager.isUserInputEnabled = false

        tabLayoutSettings()
        coinAnimationListener()
        navigationToTransaction()

        viewModel.navigationStatus.observe(viewLifecycleOwner){
            if (it == true){
                findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToTransactionFragment())
                viewModel.navigationComplete()
            }
        }


        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {

                val amount = binding.amountEditText.text.toString().toInt()
                val type = if (binding.viewPager.currentItem == 0) getString(R.string.income) else getString(R.string.spent)


                val category = when {
                    UtilManager.isSalaryClicked -> "Salary"
                    UtilManager.isHelpClicked -> "Help"
                    UtilManager.isGiftClicked-> "Gift"
                    else -> "Other"
                }

                val wallet = "main"
                val dateOfTransaction = Calendar.getInstance().time
                val comment = binding.commentEt.text.toString()

                viewModel.pushTransaction(TransactionEntity(
                        0, amount, type, category, wallet, dateOfTransaction.toString(), comment
                    )
                )
            }
        }


        binding.calendarPickerImage.setOnClickListener {
            showDatePicker()
        }

        setupCorrectDateOfDateViews()

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

                    date2daysago.setTextColor(
                        ContextCompat.getColor(requireContext(),
                        R.color.white
                    ))
                    textView2daysAgo.setTextColor(
                        ContextCompat.getColor(requireContext(),
                        R.color.white
                    ))
                    binding.twoDaysAgoContainer.background =
                        (ContextCompat.getDrawable(requireContext(),
                            R.drawable.active_datepicker_background
                        ))

                    dateToday.setTextColor(
                        ContextCompat.getColor(requireContext(),
                        R.color.black
                    ))
                    textViewToday.setTextColor(
                        ContextCompat.getColor(requireContext(),
                        R.color.black
                    ))
                    todayContainer.background = (ContextCompat.getDrawable(requireContext(),
                        R.color.white
                    ))

                    dateYesterday.setTextColor(
                        ContextCompat.getColor(requireContext(),
                        R.color.black
                    ))
                    textViewYesterday.setTextColor(
                        ContextCompat.getColor(requireContext(),
                        R.color.black
                    ))
                    yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(),
                        R.color.white
                    ))
                }

                dataViewsAnimationTwoDaysAgo(binding)
            },

            calendar.get(android.icu.util.Calendar.YEAR),
            calendar.get(android.icu.util.Calendar.MONTH),
            calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)
        )

        calendar = android.icu.util.Calendar.getInstance()
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
        if (calendar.get(android.icu.util.Calendar.DAY_OF_MONTH) == 1) {
            calendar.add(android.icu.util.Calendar.MONTH, -1)
            calendar.set(android.icu.util.Calendar.DAY_OF_MONTH, calendar.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH))
        } else calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, -1)

        // should to pass format of date (pattern) and region into SimpleDateFormat params. .format transform Date type to a string
        val correctYesterdayDateFormat =
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(calendar.time)
        binding.dateYesterday.text = correctYesterdayDateFormat
        // need to update current date for future using
        calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, +1)


        /** the day before yesterday **/
        // Need to check that the second day of the month has passed, otherwise we may get fake date
        if (calendar.get(android.icu.util.Calendar.DAY_OF_MONTH) == 1 || calendar.get(android.icu.util.Calendar.DAY_OF_MONTH) == 2 ) {
            calendar.add(android.icu.util.Calendar.MONTH, -1)
            calendar.set(android.icu.util.Calendar.DAY_OF_MONTH, calendar.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH))

            if (calendar.get(android.icu.util.Calendar.DAY_OF_MONTH) == 1) calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, -1) // if it is first day of the month
        } else calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, -2)

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



    private fun navigationToTransaction() {
        binding.backArrow.setOnClickListener { viewModel.navigationToTransaction() }
        binding.saveButton.setOnClickListener { viewModel.navigationToTransaction() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun coinAnimationListener() {
        binding.txtAddTransaction.setOnClickListener {
            val rand = (0..2).random()
            when (rand) {
               0 -> coinAnimation1(binding)
               1 -> coinAnimation2(binding)
               else -> coinAnimation3(binding)
           }
        }

        binding.coinIcon.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> coinAnimationUp(binding)
                MotionEvent.ACTION_UP -> coinAnimationDown(binding)
            }
            true
        }
    }

    class MyAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> IncomeFragment()
                1 -> SpendingFragment()
                else -> throw RuntimeException("Invalid position: $position")
            }
        }
    }

    private fun tabLayoutSettings() {
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.income_adding)
                    1 -> tab.text = getString(R.string.spent_adding)
                }
            }

        binding.viewPager.adapter = MyAdapter(this)
        tabLayoutMediator.attach()
    }



}