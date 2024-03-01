package com.example.moneymanager.Adding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneymanager.IncomeFragment
import com.example.moneymanager.SpendingFragment
import com.example.moneymanager.coinAnimation
import com.example.moneymanager.databinding.FragmentAddingBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.RuntimeException


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

        navigationToTransaction()

        coinAnimationListener()


        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Дохід"
                    1 -> tab.text = "Витрати"
                }
            }

        binding.viewPager.adapter = MyAdapter(this)
        tabLayoutMediator.attach()

    }

    private fun navigationToTransaction() {
        binding.backArrow.setOnClickListener { viewModel.navigationToTransaction() }
        binding.saveButton.setOnClickListener { viewModel.navigationToTransaction() }
    }

    private fun coinAnimationListener() {
        binding.txtAddTransaction.setOnClickListener { coinAnimation(binding) }
        binding.coinIcon.setOnClickListener { coinAnimation(binding) }
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


}