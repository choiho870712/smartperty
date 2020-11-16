package com.example.smartperty.tenant.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.tenant.notification.data.TenantNotification
import com.example.smartperty.tenant.notification.data.TenantNotificationList
import kotlinx.android.synthetic.main.tenant_fragment_notification_main.view.*

class TenantNotificationFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_notification_main, container, false)

        val notification = TenantNotificationList(
            mutableListOf(
                TenantNotification(message = "hello~~~"),
                TenantNotification(message = "ok~~~"),
                TenantNotification(message = "good")
            )
        )

        root.recycler_notification.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = TenantNOtificationAdapter(requireActivity(), root, notification.list)
        }

        return root
    }
}