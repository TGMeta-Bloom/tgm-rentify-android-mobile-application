package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class LandlordNoticeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val textView = TextView(context)
        textView.text = "Landlord: View Notices"
        textView.textSize = 24f
        textView.gravity = android.view.Gravity.CENTER
        return textView
    }
}