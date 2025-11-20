package com.example.tgmrentify.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tgmrentify.R

class FeedFragment : Fragment(R.layout.fragment_router_container) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Only add the fragment if it hasn't been added yet (prevents overlaps on rotation)
        if (childFragmentManager.findFragmentById(R.id.role_specific_container) == null) {

            // 1. Retrieve Role from Shared Preferences
            // Make sure to use the same file name and key as LoginActivity
            val sharedPref = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val userRole = sharedPref.getString("user_role", "Tenant") // Default to Tenant

            // 2. Determine which fragment to show
            val targetFragment: Fragment = if (userRole == "Landlord") {
                // Show Landlord Dashboard
                // Ideally, this should be using your LandlordFeedFragment if you want to reuse the layout I fixed earlier,
                // but based on the file search, LandlordDashboardFragment exists.
                // I will use LandlordFeedFragment which I updated with the layout logic.
                LandlordDashboardFragment()
            } else {
                // Show Tenant Feed
                TenantFeedFragment()
            }

            // 3. Embed the child fragment
            childFragmentManager.beginTransaction()
                .replace(R.id.role_specific_container, targetFragment)
                .commit()
        }
    }
}