package com.example.moneymanager.Adding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneymanager.IncomeFragment
import com.example.moneymanager.R
import com.example.moneymanager.SpendingFragment
import com.example.moneymanager.Utils.coinAnimation1
import com.example.moneymanager.Utils.coinAnimation2
import com.example.moneymanager.Utils.coinAnimation3
import com.example.moneymanager.Utils.coinAnimationDown
import com.example.moneymanager.Utils.coinAnimationUp
import com.example.moneymanager.databinding.FragmentAddingBinding
import com.google.android.material.tabs.TabLayoutMediator


class AddingFragment : Fragment() {
    private lateinit var binding: FragmentAddingBinding
    private lateinit var viewModel: AddingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddingViewModel::class.java)

        viewModel.navigationStatus.observe(viewLifecycleOwner){
            if (it == true){
                findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToTransactionFragment())
                viewModel.navigationComplete()
            }

        }
        //disable user swiping between fragments in viewPager2
        binding.viewPager.isUserInputEnabled = false

        tabLayoutSettings()
        coinAnimationListener()
        navigationToTransaction()

    }

    private fun navigationToTransaction() {
        binding.backArrow.setOnClickListener { viewModel.navigationToTransaction() }
        binding.saveButton.setOnClickListener { viewModel.navigationToTransaction() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun coinAnimationListener() {
        binding.txtAddTransaction.setOnClickListener {
            val rand = (0..2).random()
            when (rand) {
               0 -> coinAnimation1(binding)
               1 -> coinAnimation2(binding)
               else -> coinAnimation3(binding)
           }
        }

        binding.coinIcon.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> coinAnimationUp(binding)
                MotionEvent.ACTION_UP -> coinAnimationDown(binding)
            }
            true
        }
    }

    class MyAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> IncomeFragment()
                1 -> SpendingFragment()
                else -> throw RuntimeException("Invalid position: $position")
            }
        }
    }

    private fun tabLayoutSettings() {
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.income_adding)
                    1 -> tab.text = getString(R.string.spent_adding)
                }
            }

        binding.viewPager.adapter = MyAdapter(this)
        tabLayoutMediator.attach()
    }

}