package com.example.moneymanager.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.TransactionAdapter.TransactionAdapter
import com.example.moneymanager.databinding.FragmentTransactionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding


    // Get a ViewModel instance
    /*private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(requireActivity().application)
    }*/

    /*private val viewModel: TransactionViewModel by lazy {
        val factory = TransactionViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory).get(TransactionViewModel::class.java)
    }*/


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



                CoroutineScope(Dispatchers.Main).launch {
                    val transactionsList = viewModel.getAllTransactions()
                    transactionsList.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                }


        binding.testAdd.setOnClickListener {
                viewModel.fetchEntities()
            }

        viewModel.navigationStatus.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(TransactionFragmentDirections.actionTransactionFragmentToAddingFragment())
                viewModel.navigationComplete()
            }

        }



       /* navigationToAdding()*/
        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding() }

    }


    /*private fun navigationToAdding() {  }*/


}