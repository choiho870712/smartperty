package com.example.smartperty.tenant.home.attractionsNearby


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.smartperty.R

/**
 * A simple [Fragment] subclass.
 */
class TenantAttractionsNearbyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tenant_fragment_attractions_nearby, container, false)
    }


}
