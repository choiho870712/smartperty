package com.smartperty.smartperty.landlord.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.notification.data.LandlordNotification
import com.smartperty.smartperty.landlord.notification.data.LandlordNotificationList
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

        val notification = LandlordNotificationList(
            mutableListOf(
                LandlordNotification(message = "2020年12月5日 Jason 的房租到期"),
                LandlordNotification(message = "Jason 的冷氣機維修\n已完成"),
                LandlordNotification(message = "萬聖節快樂~~")
            )
        )

        root.recycler_notification.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordNotificationAdapter(requireActivity(), root, notification.list)
        }

        return root
    }
}