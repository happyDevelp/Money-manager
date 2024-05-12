package com.example.moneymanager.Spending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.moneymanager.Income.UtilManager
import com.example.moneymanager.R
import com.example.moneymanager.Spending.SpendingUtilityManager.clothesIsClicked
import com.example.moneymanager.Spending.SpendingUtilityManager.eatIsClicked
import com.example.moneymanager.Spending.SpendingUtilityManager.houseIsClicked
import com.example.moneymanager.Spending.SpendingUtilityManager.techniqueIsClicked
import com.example.moneymanager.databinding.FragmentSpendingBinding

class SpendingFragment : Fragment() {
    private lateinit var binding: FragmentSpendingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSpendingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryContainerClickListener()
    }

    private fun categoryContainerClickListener() {
        binding.eatContainer.setOnClickListener {
            binding.apply {
                eatContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.category_yellow_active_background)
                clothesContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                techniqueContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                householdContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                eatImv.background.alpha = 0
                clothesImv.background.alpha = 255
                techniqueImv.background.alpha = 255
                householdImv.background.alpha = 255
                otherContainer.background.alpha = 255

                eatIsClicked = true
                clothesIsClicked = false
                techniqueIsClicked = false
                houseIsClicked = false

                UtilManager.categoryIsChanged = true
                if (UtilManager.amountIsNotNull) UtilManager.buttonIsClickable.value = true
            }
        }

        binding.clothesContainer.setOnClickListener {
            binding.apply {
                eatContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                clothesContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.category_burgundy_active_background)
                techniqueContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                householdContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                eatImv.background.alpha = 255
                clothesImv.background.alpha = 0
                techniqueImv.background.alpha = 255
                householdImv.background.alpha = 255
                otherImv.background.alpha = 255

                eatIsClicked = false
                clothesIsClicked = true
                techniqueIsClicked = false
                houseIsClicked = false

                UtilManager.categoryIsChanged = true
                if (UtilManager.amountIsNotNull) UtilManager.buttonIsClickable.value = true
            }
        }

        binding.techniqueContainer.setOnClickListener {
            binding.apply {
                eatContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                clothesContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                techniqueContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.category_blue_active_background)
                householdContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                eatImv.background.alpha = 255
                clothesImv.background.alpha = 255
                techniqueImv.background.alpha = 0
                householdImv.background.alpha = 255
                otherImv.background.alpha = 255

                eatIsClicked = false
                clothesIsClicked = false
                techniqueIsClicked = true
                houseIsClicked = false

                UtilManager.categoryIsChanged = true
                if (UtilManager.amountIsNotNull) UtilManager.buttonIsClickable.value = true
            }
        }

        binding.householdContainer.setOnClickListener {
            binding.apply {
                eatContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                clothesContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                techniqueContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                householdContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.category_tuquoise_active_background)
                otherContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)

                eatImv.background.alpha = 255
                clothesImv.background.alpha = 255
                techniqueImv.background.alpha = 255
                householdImv.background.alpha = 0
                otherImv.background.alpha = 255

                eatIsClicked = false
                clothesIsClicked = false
                techniqueIsClicked = false
                houseIsClicked = true

                UtilManager.categoryIsChanged = true
                if (UtilManager.amountIsNotNull) UtilManager.buttonIsClickable.value = true
            }
        }

        binding.otherContainer.setOnClickListener {
            binding.apply {
                eatContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                clothesContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                techniqueContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                householdContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent_layout)
                otherContainer.background = ContextCompat.getDrawable(requireContext(), R.drawable.category_beige_active_background)

                eatImv.background.alpha = 255
                clothesImv.background.alpha = 255
                techniqueImv.background.alpha = 255
                householdImv.background.alpha = 255
                otherImv.background.alpha = 0

                eatIsClicked = false
                clothesIsClicked = false
                techniqueIsClicked = false
                houseIsClicked = false

                UtilManager.categoryIsChanged = true
                if (UtilManager.amountIsNotNull) UtilManager.buttonIsClickable.value = true
            }
        }
    }

}

object SpendingUtilityManager {
    var eatIsClicked: Boolean = false
    var clothesIsClicked: Boolean = false
    var techniqueIsClicked: Boolean = false
    var houseIsClicked: Boolean = false
}