package com.example.moneymanager.Adding

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.Details.DetailUtils
import com.example.moneymanager.Income.IncomeFragment
import com.example.moneymanager.Income.UtilManager
import com.example.moneymanager.Income.UtilManager.amountIsNotNull
import com.example.moneymanager.Income.UtilManager.buttonIsClickable
import com.example.moneymanager.Income.UtilManager.categoryIsChanged
import com.example.moneymanager.R
import com.example.moneymanager.Spending.SpendingFragment
import com.example.moneymanager.Spending.SpendingUtilityManager
import com.example.moneymanager.Utils.Cache
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddingFragment : Fragment() {
    lateinit var binding: FragmentAddingBinding
    private val viewModel: AddingViewModel by viewModel()
    private var calendar = Calendar.getInstance()
    lateinit var  cache: Cache

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cache = Cache(requireContext())
       /* viewModel = ViewModelProvider(this).get(AddingViewModel::class.java)*/
        //disable user swiping between fragments in viewPager2
        binding.viewPager.isUserInputEnabled = false

        tabLayoutSettings()
        coinAnimationListener()
        navigationToTransaction()
        changeHintOfEdTxt()
        setupCorrectDateOfDateViews()
        checkingAmountInput()

        buttonIsClickable.observe(viewLifecycleOwner){ if (it == true) binding.saveButton.isEnabled = true }
        binding.saveButton.setOnClickListener { saveButtonClicking() }
        binding.calendarPickerImage.setOnClickListener { showDatePicker() }

        viewModel.navigationStatus.observe(viewLifecycleOwner){
            if (it == true){
                findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToTransactionFragment())
                viewModel.navigationComplete()
            }
        }

        val getImage = pickImageClickListener()
        binding.addPhotoImageView.setOnClickListener { getImage.launch("image/*") }

        if (DetailUtils.editItem) { restoringValues() }

      /*  val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_delete_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.delete_dialog_background))
        dialog.setCancelable(true)*/

    }


    private fun saveImage(imageURI: Uri?) : String {
        val inputStream: InputStream? = imageURI?.let { context?.contentResolver?.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(inputStream) //create bitmap object from stream
        inputStream?.close()

        return if (bitmap != null) cache.saveToCacheAndGetUri(bitmap).toString() else "no photo"
    }

    private fun pickImageClickListener(): ActivityResultLauncher<String> {
        val getImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imageGalleryURI ->
                viewModel.imageGalleryUri = imageGalleryURI
                if (imageGalleryURI != null) {
                    imageSetup(imageGalleryURI)
                }
            }
        return getImage
    }

    private fun imageSetup(imageGalleryURI: Uri?) {
        binding.addPhotoImageView.apply {
            setImageURI(imageGalleryURI)
            background = null
            setPadding(0, 0, 0, 0)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }


    private fun saveButtonClicking() {
            lifecycleScope.launch {
                val amount = binding.amountEditText.text.toString().toInt()

                val type = if (binding.viewPager.currentItem == 0) getString(R.string.income_adding)
                else getString(R.string.spent_adding)

                val category: String = if (type == context?.getString(R.string.income_adding)) {
                    when {
                        UtilManager.isSalaryClicked -> "Salary"
                        UtilManager.isHelpClicked -> "Help"
                        UtilManager.isGiftClicked -> "Gift"
                        else -> "Other"
                    }
                } else when {
                    SpendingUtilityManager.eatIsClicked -> "Eat"
                    SpendingUtilityManager.clothesIsClicked -> "Clothes"
                    SpendingUtilityManager.techniqueIsClicked -> "Technique"
                    SpendingUtilityManager.houseIsClicked -> "household"
                    else -> "Other"
                }

                val wallet = "main"
                val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
                val current = dateFormat.format(calendar.time)
                val comment = binding.commentEt.text.toString()
                val imageUri: String = saveImage(viewModel.imageGalleryUri)

                val transactionUnit =
                    TransactionEntity(0, amount, type, category, wallet, current, comment, imageUri, false)

                //bad idea using editItem?
                if (!DetailUtils.editItem) { viewModel.pushTransaction(transactionUnit) }
                else {
                    viewModel.updateTransaction(amount, type, category, wallet, current, comment, imageUri, DetailUtils.id)
                    DetailUtils.editItem = false
                    DetailUtils.id = 0
                }


                UtilManager.reset()
                findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToTransactionFragment())
            }

    }

    private fun checkingAmountInput() {
        binding.amountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (categoryIsChanged && s.toString() != "" && s.toString().toInt() != 0) {
                    binding.saveButton.isEnabled = true
                    amountIsNotNull = true
                } else {
                    binding.saveButton.isEnabled = false
                    amountIsNotNull = if (s.toString() != "" && s.toString().toInt() != 0) true
                    else false
                }
            }
        })
    }

    private fun changeHintOfEdTxt() {
        binding.amountEditText.setOnFocusChangeListener { view, hasFokus ->
            if (hasFokus) binding.amountEditText.hint = ""
            else {
                if (binding.amountEditText.text.isEmpty()) binding.amountEditText.hint = "0"
            }
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
                    textView2daysAgo.text = getString(R.string.self_selected_date)

                    date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.twoDaysAgoContainer.background = (ContextCompat.getDrawable(requireContext(), R.drawable.active_datepicker_background))

                    dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    todayContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))

                    dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))
                }
                dataViewsAnimationTwoDaysAgo(binding)
            },

            calendar.get(android.icu.util.Calendar.YEAR),
            calendar.get(android.icu.util.Calendar.MONTH),
            calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)
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
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH))
        } else calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, -1)

        // should to pass format of date (pattern) and region into SimpleDateFormat params. .format transform Date type to a string
        val correctYesterdayDateFormat =
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(calendar.time)
        binding.dateYesterday.text = correctYesterdayDateFormat
        // need to update current date for future using
        calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, +1)


        /** the day before yesterday **/
        // Need to check that the second day of the month has passed, otherwise we may get fake date
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1 || calendar.get(android.icu.util.Calendar.DAY_OF_MONTH) == 2 ) {
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH))

            if (calendar.get(android.icu.util.Calendar.DAY_OF_MONTH) == 1) calendar.add(android.icu.util.Calendar.DAY_OF_MONTH, -1) // if it is first day of the month
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

    private fun todaySelected() {
        binding.todayContainer.setOnClickListener {
            binding.apply {
                dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                it.background = (ContextCompat.getDrawable(requireContext(), R.drawable.active_datepicker_background))

                dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))

                date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                twoDaysAgoContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))
            }
            dataViewsAnimationToday(binding)

        }
    }

    private fun yesterdaySelected() {
        binding.yesterdayContainer.setOnClickListener {
            binding.apply {
                dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                it.background = (ContextCompat.getDrawable(requireContext(), R.drawable.active_datepicker_background))

                dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                todayContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))

                date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                twoDaysAgoContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))
            }

            dataViewsAnimationYesterday(binding)
        }
    }

    private fun twoDaysAgoSelected() {
        binding.twoDaysAgoContainer.setOnClickListener {
            binding.apply {
                date2daysago.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textView2daysAgo.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                it.background = (ContextCompat.getDrawable(requireContext(), R.drawable.active_datepicker_background))

                dateToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewToday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                todayContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))

                dateYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                textViewYesterday.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                yesterdayContainer.background = (ContextCompat.getDrawable(requireContext(), R.color.white))
            }

            dataViewsAnimationTwoDaysAgo(binding)
        }
    }

    private fun restoringValues() {
        binding.apply {
            lifecycleScope.launch {
                val item = viewModel.getTransactionById(DetailUtils.id)
                amountEditText.setText(item.amount.toString())
                textView2daysAgo.text = getString(R.string.self_selected_date)
                txtAddTransaction.text = "Change Transaction"

                date2daysago.text = item.dateOfTransaction.trim().substringBefore(" ")
                twoDaysAgoSelected()

                commentEt.setText(item.comment.toString())
                if (item.imageUri != "no photo") imageSetup(item.imageUri?.toUri())
                DetailUtils.editItem = false
            }
        }
    }

    private fun navigationToTransaction() {
        binding.backArrow.setOnClickListener {
            viewModel.navigationToTransaction()
            UtilManager.reset()
        }
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
        override fun getItemCount(): Int { return 2 }

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

    override fun onDestroy() {
        UtilManager.reset()
        super.onDestroy()
    }
}