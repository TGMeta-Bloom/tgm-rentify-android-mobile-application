package com.example.tgmrentify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class BrowsePropertiesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val textView = TextView(context)
        textView.text = "Tenant: Browse Properties"
        textView.textSize = 24f
        textView.gravity = android.view.Gravity.CENTER
        return textView
    }
}