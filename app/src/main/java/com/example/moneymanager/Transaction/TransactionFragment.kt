package com.example.moneymanager.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.TransactionAdapter.TransactionAdapter
import com.example.moneymanager.databinding.FragmentTransactionBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.util.Calendar

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val viewModel: TransactionViewModel by viewModel()
    private val calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionAdapter()
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.transactions.observe(viewLifecycleOwner) { checkDataExist(it) }

            lifecycleScope.launch { monthView(adapter) }

        viewModel.navigationStatus.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToAddingFragment())
                viewModel.navigationComplete()
            }
        }

        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding() }

        binding.btnFavourite.setOnClickListener {
            findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToFavouritesFragment())
        }

        searchInputTextListener(adapter)
        itemRWClickListener(adapter)
    }

    private suspend fun TransactionFragment.monthView(adapter: TransactionAdapter) {
        val monthList = listOf<String>(
            "Jan.", "Feb.", "Mar.", "Apr.", "May.",
            "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."
        )

        var monthNum = calendar.get(Calendar.MONTH)
        var yearNum = calendar.get(Calendar.YEAR)

        binding.tvMonth.text = getString(R.string.calendar_month_year, monthList[monthNum], yearNum.toString())

        checkOpportunityToChangeMonth(monthList)
        adapterListSubmit(adapter, monthNum, yearNum)

        val month = if (monthNum < 10) "0${monthNum + 1}" else "${monthNum + 1}"
        fillCells((month), yearNum)

        binding.monthBack.setOnClickListener {
            monthBackClickListener(monthNum, yearNum, monthList, adapter)
        }

        binding.monthForward.setOnClickListener {
            monthForwardClickListener(monthNum, yearNum, monthList, adapter)
        }
    }

    private fun monthBackClickListener(monthNum: Int, yearNum: Int, monthList: List<String>, adapter: TransactionAdapter) {
        var monthNum1 = monthNum
        var yearNum1 = yearNum
        lifecycleScope.launch {
            if (monthNum1 == 0) {
                monthNum1 = 11
                yearNum1 -= 1
            } else monthNum1 -= 1

            binding.tvMonth.text =
                getString(R.string.calendar_month_year, monthList[monthNum1], yearNum1.toString())

            adapterListSubmit(adapter, monthNum1, yearNum1)

            val month = if (monthNum1 < 10) "0${monthNum1 + 1}" else "${monthNum1 + 1}"
            fillCells(month, yearNum1)
            checkOpportunityToChangeMonth(monthList)
        }
    }

    private fun monthForwardClickListener(monthNum: Int, yearNum: Int, monthList: List<String>, adapter: TransactionAdapter) {
        var monthNum1 = monthNum
        var yearNum1 = yearNum
        lifecycleScope.launch {
            if (monthNum1 == 11) {
                monthNum1 = 0
                yearNum1 += 1
            } else monthNum1 += 1

            binding.tvMonth.text =
                getString(R.string.calendar_month_year, monthList[monthNum1], yearNum1.toString())
            adapterListSubmit(adapter, monthNum1, yearNum1)

            val month = if (monthNum1 < 10) "0${monthNum1 + 1}" else "${monthNum1 + 1}"
            fillCells(month, yearNum1)
            checkOpportunityToChangeMonth(monthList)
        }
    }

    private fun checkOpportunityToChangeMonth(monthList: List<String>) {
        binding.apply {
            if (tvMonth.text == "${monthList[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}") {
                monthForward.isEnabled = false
                monthForward.alpha = 0.25f
            } else {
                monthForward.isEnabled = true
                monthForward.alpha = 1f
            }
        }
    }

    private suspend fun adapterListSubmit(adapter: TransactionAdapter, monthNum: Int, yearNum: Int) {
        val date = if ((monthNum + 1) < 10) "0${(monthNum + 1)}" else "${(monthNum + 1)}"
        val transactionList = viewModel.getTransactionsByMonth(date, yearNum.toString())
        adapter.submitList(transactionList)
    }

    private fun searchInputTextListener(adapter: TransactionAdapter) {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    if (newText != "")
                        adapter.submitList(viewModel.searchTransaction("%${newText}%"))
                }
                return true
            }
        })
    }

    private fun itemRWClickListener(adapter: TransactionAdapter) {
        adapter.setOnClickListener(object : TransactionAdapter.OnClickListener {
            override fun onItemClick(itemId: Int) {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToDetailsFragment(itemId))
                viewModel.navigationComplete()
            }
        })
    }

    private fun checkDataExist(list: List<TransactionEntity>) {
        if (list.isEmpty()) {
            binding.txtNoDataInfo.visibility = View.VISIBLE
            binding.hintArrow.visibility = View.VISIBLE
        } else {
            binding.txtNoDataInfo.visibility = View.INVISIBLE
            binding.hintArrow.visibility = View.INVISIBLE
        }
    }

    private fun fillCells(month: String, year: Int) {
        lifecycleScope.launch {

            val income = viewModel.getSumByType(getString(R.string.income_adding), month, year.toString())
            val spend = viewModel.getSumByType(getString(R.string.spent_adding), month, year.toString())
            val balance = income - spend

            binding.txtIncome.text = getString(R.string.income_cell, getString(R.string.income_adding), income.delimiterFormat())
            binding.txtSpend.text = getString(R.string.spend_cell, getString(R.string.spent_adding), spend.delimiterFormat())
            binding.txtBalance.text = getString(R.string.balance_cell, balance.delimiterFormat())
        }
    }

    private fun Int.delimiterFormat():String {
        val numberFormat = DecimalFormat("##,###")
        return numberFormat.format(this)
    }

}