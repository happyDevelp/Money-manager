package com.example.moneymanager.DetailsFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.DetailsFragment.DetailUtils.editItem
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()

    lateinit var dialog: Dialog
    lateinit var btnDialogCancel: Button
    lateinit var btnDialogDelete: Button

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

        //val dialogg = DeleteDialogFragment(requireContext())
        dialogDelete(itemId)

        binding.editIcon.setOnClickListener {
            editItem = true
            DetailUtils.id = itemId
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToAddingFragment())
        }

    }

    private fun dialogDelete(itemId: Int) {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_delete_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delete_dialog_background))
        dialog.setCancelable(true)

        btnDialogDelete = dialog.findViewById(R.id.delete_button)
        btnDialogCancel = dialog.findViewById(R.id.cancel_button)

        btnDialogDelete.setOnClickListener {
            dialog.dismiss()
            lifecycleScope.launch { viewModel.deleteTransactionById(itemId) }
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToTransactionFragment())
        }

        btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.containerDelete.setOnClickListener {
            dialog.show()
        }
    }

}

object DetailUtils{
    var editItem: Boolean = false
    var id: Int = 0
}
