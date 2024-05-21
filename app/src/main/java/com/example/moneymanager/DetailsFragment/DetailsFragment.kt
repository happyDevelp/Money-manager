package com.example.moneymanager.DetailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item: TransactionEntity
        val itemId = args.itemId

        lifecycleScope.launch {
            item = viewModel.getTransactionById(itemId)

            with(binding) {
                txtNumericAmount.text = getString(R.string.amount_details, item.amount.toString())
                txtActuallyTypeOfTransaction.text = item.transactionType
                txtSelectedCategory.text = item.transactionCategory

                if (item.comment != "") txtActuallyComment.text = item.comment
                txtActuallyDateOfTransaction.text = item.dateOfTransaction

                if (item.imageUri == "no photo") txtPhoto.visibility = View.GONE
                categoryPhoto.setImageURI(item.imageUri?.toUri())
            }
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToTransactionFragment())
        }

        binding.containerDelete.setOnClickListener {
            lifecycleScope.launch { viewModel.deleteTransactionById(itemId) }
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToTransactionFragment())
        }

    }

}