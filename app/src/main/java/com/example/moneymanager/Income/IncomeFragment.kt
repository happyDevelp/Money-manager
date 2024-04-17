package com.example.moneymanager.Income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentIncomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IncomeFragment : Fragment() {
    private lateinit var db: DataBase

    lateinit var binding: FragmentIncomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DataBase.getInstance(requireContext())

        CategoryContainterClickListener()

    }


    private suspend fun insertTransaction(transaction: TransactionEntity)  {
        return withContext(Dispatchers.IO) {
            db.DAO.insertTransaction(transaction)
        }
    }


    private fun CategoryContainterClickListener() {
        binding.salaryContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.category_green_active_background )
                helpContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.transparent_layout
                )
                giftContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.transparent_layout
                )
                otherContainer.background = ContextCompat.getDrawable(requireContext(),
                    R.drawable.transparent_layout
                )

                salaryImv.background.alpha = 0
                helpImv.background.alpha = 255
                giftImv.background.alpha = 255
                otherImv.background.alpha = 255

                UtilManager.isSalaryClicked = true
                UtilManager.isHelpClicked = false
                UtilManager.isGiftClicked = false
            }
        }

        binding.helpContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                helpContainer.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.category_blue_active_background
                )
                giftContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                salaryImv.background.alpha = 255
                helpImv.background.alpha = 0
                giftImv.background.alpha = 255
                otherImv.background.alpha = 255

                UtilManager.isSalaryClicked = false
                UtilManager.isHelpClicked = true
                UtilManager.isGiftClicked = false
            }
        }

        binding.giftContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                helpContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                giftContainer.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.category_pink_active_background
                )
                otherContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                salaryImv.background.alpha = 255
                helpImv.background.alpha = 255
                giftImv.background.alpha = 0
                otherImv.background.alpha = 255

                UtilManager.isSalaryClicked = false
                UtilManager.isHelpClicked = false
                UtilManager.isGiftClicked = true
            }
        }

        binding.otherContainer.setOnClickListener {
            binding.apply {
                salaryContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                helpContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                giftContainer.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.category_beige_active_background
                )

                salaryImv.background.alpha = 255
                helpImv.background.alpha = 255
                giftImv.background.alpha = 255
                otherImv.background.alpha = 0

                UtilManager.isSalaryClicked = false
                UtilManager.isHelpClicked = false
                UtilManager.isGiftClicked = false
            }
        }
    }


}

object UtilManager {
    var isSalaryClicked: Boolean = false
    var isHelpClicked: Boolean = false
    var isGiftClicked: Boolean = false

    var comment: String = "s"
}