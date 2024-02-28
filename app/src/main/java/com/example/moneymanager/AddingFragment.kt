package com.example.moneymanager

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moneymanager.databinding.FragmentAddingBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.RuntimeException


class AddingFragment : Fragment() {
    private lateinit var binding: FragmentAddingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToTransactionFragment())
        }

        binding.saveButton.setOnClickListener {
            findNavController().navigate(AddingFragmentDirections.actionAddingFragmentToTransactionFragment())
        }

        binding.txtAddTransaction.setOnClickListener { coinAnimation(binding) }


        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Дохід"
                    1 -> tab.text = "Витрати"
                }
            }

        binding.viewPager.adapter = MyAdapter(this)
        tabLayoutMediator.attach()

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