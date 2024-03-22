package com.example.moneymanager.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.TransactionAdapter.TransactionAdapter
import com.example.moneymanager.databinding.FragmentTransactionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding

    // Get a ViewModel instance
    private lateinit var viewModel: TransactionViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        val adapter = TransactionAdapter()
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())


        CoroutineScope(Dispatchers.Main).launch {
            val transactionsList = viewModel.getAllTransactions()
            transactionsList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }


        viewModel.navigationStatus.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToAddingFragment())
                viewModel.navigationComplete()
            }

        }

        navigationToAdding()

    }



    private fun navigationToAdding() {
        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding()

        }
    }


}