package com.example.moneymanager.Transaction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    lateinit var binding: FragmentTransactionBinding

    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get a ViewModel instance
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.navigationStatus.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToAddingFragment())
                viewModel.navigationComplete()
            }

        }

        navigationToAdding()

    }

    private fun navigationToAdding() {
        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding() }
    }


}