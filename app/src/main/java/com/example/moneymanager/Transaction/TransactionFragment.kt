package com.example.moneymanager.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.TransactionAdapter.TransactionAdapter
import com.example.moneymanager.databinding.FragmentTransactionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        //test uri from db
/*         lifecycleScope.launch {
             val picUri = viewModel.getTransactionById(1).imageUri
             binding.testImage.setImageURI(picUri.toUri())
        }*/

       /* navigationToAdding()*/
        binding.floatAddButton.setOnClickListener { viewModel.navigationToAdding() }

    }


}