package com.example.moneymanager.PieChart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentPieChartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PieChartFragment : Fragment() {
    lateinit var binding: FragmentPieChartBinding
    val viewModel: PieChartViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPieChartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showChart(getString(R.string.income_adding))
        legendSetup()

        binding.incomeBtn.setOnClickListener { incomeButtonClicked(it) }
        binding.spendBtn.setOnClickListener { spendButtonClicked(it) }
    }

    private fun showChart(transactionType: String) {
        var transactions: List<TransactionEntity>
        val categories = mutableMapOf<String, Int>()

        lifecycleScope.launch {
                transactions = viewModel.getTransactionsByType(transactionType)

                for (item in transactions) {
                    if (!categories.containsKey(item.transactionCategory))
                        categories.put(item.transactionCategory, item.amount)
                    else
                        categories[item.transactionCategory] = categories[item.transactionCategory]!!.plus(item.amount)
                }

                val entriesList = mutableListOf<PieEntry>()

                for (item in categories) {
                    entriesList.add(PieEntry(item.value.toFloat(), item.key))
                    Log.i("TestPie", entriesList.toString())
                }

                val pieDataSet = PieDataSet(entriesList, null)

                val colors = listOf(
                    Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
                    Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)
                )

                pieDatasetSetup(pieDataSet, colors)

                val pieData = PieData(pieDataSet)
                pieChartSetup(pieData)

                switchStateClickListener(pieDataSet)
        }
    }

    private fun spendButtonClicked(it: View) {
        it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.btn_spend))
        binding.incomeBtn.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.thick_outline)
        binding.title.text = getString(R.string.spent_adding)
        showChart(getString(R.string.spent_adding))
        binding.chart.invalidate()
    }

    private fun incomeButtonClicked(it: View) {
        it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.btn_income))
        binding.spendBtn.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.thick_outline)
        binding.title.text = getString(R.string.income_adding)
        showChart(getString(R.string.income_adding))
        binding.chart.invalidate()
    }

    private fun pieDatasetSetup(pieDataSet: PieDataSet, colors: List<Int>) {
        pieDataSet.apply {
            setColors(colors.shuffled())
            valueTextSize = 10f
            valueTextColor = Color.WHITE
            valueFormatter = PercentFormatter()
        }
    }

    private fun pieChartSetup(pieData: PieData) {
        binding.chart.apply {
            data = pieData
            description.isEnabled = false


            animateY(1000)
            setHoleColor(Color.parseColor("#16BF690B"))
            invalidate()
        }
    }

    private fun legendSetup() {
        binding.chart.legend.apply {
            yOffset = 5f
            formSize = 25f
            form = Legend.LegendForm.CIRCLE
            textSize = 12f
            xEntrySpace = 15f
        }
    }

    private fun switchStateClickListener(pieDataSet: PieDataSet) {
        if (binding.switchPercent.isChecked) {
            binding.chart.setUsePercentValues(true)
            binding.switchStateText.text = getString(R.string.switch_state_on)
        } else {
            binding.chart.setUsePercentValues(false)
            binding.switchStateText.text = getString(R.string.switch_state_off)
        }

        binding.switchPercent.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                pieDataSet.valueFormatter = PercentFormatter(binding.chart)
                binding.chart.setUsePercentValues(true)
                binding.switchStateText.text = getString(R.string.switch_state_on)
            } else {
                binding.chart.setUsePercentValues(false)
                binding.switchStateText.text = getString(R.string.switch_state_off)
            }
            binding.chart.invalidate()
        }
    }

}