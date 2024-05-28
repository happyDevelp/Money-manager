package com.example.moneymanager.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.R
import com.example.moneymanager.TransactionAdapter.TransactionAdapter
import com.example.moneymanager.databinding.FragmentTransactionBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val viewModel: TransactionViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionAdapter()
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.transactions.observe(viewLifecycleOwner) { adapter.submitList(it) }

        viewModel.navigationStatus.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToAddingFragment())
                viewModel.navigationComplete()
            }
        }

        adapter.setOnClickListener(object : TransactionAdapter.OnClickListener {
            override fun onItemClick(itemId: Int) {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToDetailsFragment(itemId))
                viewModel.navigationComplete()
            }
        })

        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding() }

        fillCells()
    }

    private fun fillCells() {
        lifecycleScope.launch {
            val income = viewModel.getSumByType(getString(R.string.income_adding))
            val spend = viewModel.getSumByType(getString(R.string.spent_adding))
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