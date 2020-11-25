package com.smartperty.smartperty.tenant.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tenant.notification.data.TenantNotification
import com.smartperty.smartperty.tenant.notification.data.TenantNotificationList
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolBarUtils
import kotlinx.android.synthetic.main.tenant_fragment_notification_main.view.*

class TenantNotificationFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_notification_main, container, false)

        toolBarUtils.setTitle("")
        toolBarUtils.removeAllButtonAndLogo()

        val notification = TenantNotificationList(
            mutableListOf(
                TenantNotification(message = "2020年12月5日房租到期"),
                TenantNotification(message = "冷氣機維修\n已完成"),
                TenantNotification(message = "萬聖節快樂")
            )
        )

        root.recycler_notification.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = TenantNotificationAdapter(requireActivity(), root, notification.list)
        }

        return root
    }
}