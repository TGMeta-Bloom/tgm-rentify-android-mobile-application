package com.example.tgmrentify.view
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tgmrentify.R
class FeedFragment : Fragment(R.layout.fragment_router_container)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.role_specific_container) == null) {
            val sharedPref = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)

            val userRole = sharedPref.getString("user_role", "tenant")

            val targetFragment: Fragment = if (userRole == "Landlord") LandlordDashboardFragment() else TenantFeedFragment()

            childFragmentManager.beginTransaction().replace(R.id.role_specific_container, targetFragment).commit()
        }
    }
}