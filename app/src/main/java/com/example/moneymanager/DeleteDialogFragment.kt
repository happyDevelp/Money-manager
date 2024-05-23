package com.example.moneymanager

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moneymanager.databinding.FragmentDeleteDialogBinding

/** THIS CLASS NOT USED YET **/
class DeleteDialogFragment(val myContext: Context) : Fragment() {
    lateinit var dialog: Dialog
    lateinit var binding: FragmentDeleteDialogBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDeleteDialogBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Dialog(myContext)

    }

    /*fun showCustomDialog() {
        dialog.setContentView(R.layout.fragment_delete_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(myContext,R.drawable.delete_dialog_background))
        dialog.setCancelable(true)

        binding.deleteButton.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(DeleteDialogFragmentDirections.actionDeleteDialogFragment2ToTransactionFragment())
        }

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }*/
}



