package com.smartperty.smartperty.landlord.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.tenant_fragment_notification_main.view.*

class LandlordNotificationFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_notification_main, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root.recycler_notification.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordNotificationAdapter(requireActivity(), root,
                GlobalVariables.notificationList)
        }

        return root
    }
}