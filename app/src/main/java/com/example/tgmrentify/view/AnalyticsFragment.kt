package com.example.tgmrentify.view

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tgmrentify.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Back Button Logic
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // 2. Load the Chart
        setupPieChart()

        // 3. Animate the Numbers (The cool part!)
        animateNumber(binding.tvCountPosts, 50)
        animateNumber(binding.tvCountVotes, 100)
        animateNumber(binding.tvCountSaved, 20)
    }

    private fun setupPieChart() {
        val pieChart = binding.pieChart

        // Create Data
        val entries = listOf(
            PieEntry(40f, "Apartments"),
            PieEntry(30f, "House"),
            PieEntry(20f, "Villa"),
            PieEntry(10f, "Annex")
        )

        // Colors for the slices (Blue shades to match your theme)
        val colors = listOf(
            Color.parseColor("#4FC3F7"), // Light Blue
            Color.parseColor("#0288D1"), // Dark Blue
            Color.parseColor("#81D4FA"), // Lighter Blue
            Color.parseColor("#01579B")  // Darkest Blue
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        pieChart.data = data

        // Styling the Chart
        pieChart.description.isEnabled = false
        pieChart.centerText = "Types"
        pieChart.setCenterTextSize(14f)
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.legend.isEnabled = false // Hide legend to keep it clean like design

        // ANIMATION: Spin the chart on load!
        pieChart.animateY(1400)
        pieChart.invalidate()
    }

    // Helper function to count up numbers smoothly
    private fun animateNumber(textView: TextView, finalValue: Int) {
        val animator = ValueAnimator.ofInt(0, finalValue)
        animator.duration = 1500 // Animation takes 1.5 seconds
        animator.addUpdateListener { animation ->
            textView.text = animation.animatedValue.toString()
        }
        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}