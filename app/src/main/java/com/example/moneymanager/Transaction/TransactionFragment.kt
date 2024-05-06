package com.example.moneymanager.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.TransactionAdapter.TransactionAdapter
import com.example.moneymanager.databinding.FragmentTransactionBinding
import kotlinx.coroutines.launch

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        /*viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = DataBase.getInstance(application).DAO
        val viewModelFactory = TransactionViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TransactionViewModel::class.java)

        val adapter = TransactionAdapter()
        binding.recycleView.adapter = adapter

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

        //Підписка
        /*viewModel.transactions.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.fetchEntities()*/


        lifecycleScope.launch {
            val transactionsList = viewModel.getAllTransactions()
            transactionsList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }

        binding.testAdd.setOnClickListener { viewModel.fetchEntities() }

        viewModel.navigationStatus.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToAddingFragment())
                viewModel.navigationComplete()
            }
        }

        //test uri from db
/*         lifecycleScope.launch {
             val picUri = viewModel.getTransactionById(1).imageUri
             binding.testImage.setImageURI(picUri.toUri())
        }*/

       /* navigationToAdding()*/
        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding() }

    }


}