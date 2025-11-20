package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class LandlordDashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Temporary Text View just to prove it works
        val textView = TextView(context)
        textView.text = "Landlord Feed Screen (Friend's Work)"
        textView.textSize = 24f
        textView.gravity = android.view.Gravity.CENTER
        return textView
    }
}