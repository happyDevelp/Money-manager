package com.example.moneymanager.Settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    val viewModel: SettingsViewModel by viewModel()

    lateinit var dialog: Dialog
    lateinit var btnDialogCancel: Button
    lateinit var btnDialogDelete: Button
    lateinit var textTitleDialogDelete: TextView
    lateinit var textDescriptionDialogDelete: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      dialogWipeData()

    }

    private fun dialogWipeData() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_delete_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.delete_dialog_background))
        dialog.setCancelable(true)

        btnDialogDelete = dialog.findViewById(R.id.delete_button)
        btnDialogCancel = dialog.findViewById(R.id.cancel_button)

        textTitleDialogDelete = dialog.findViewById(R.id.txt_title)
        textTitleDialogDelete.text = getString(R.string.confirm_wipe_all_data)

        textDescriptionDialogDelete = dialog.findViewById(R.id.txt_question)
        textDescriptionDialogDelete.text = getString(R.string.title_wipe_all_data)

        btnDialogDelete.setOnClickListener {
            dialog.dismiss()
            lifecycleScope.launch {
                viewModel.deleteAllTransactions()
            }
            Toast.makeText(requireContext(), "All data was successfully deleted", Toast.LENGTH_SHORT).show()
        }

        btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnWipeData.setOnClickListener {
            dialog.show()
        }
    }

}

