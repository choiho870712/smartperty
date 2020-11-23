package com.example.smartperty.landlord.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.landlord.chat.data.LandlordChat
import com.example.smartperty.tenant.notification.TenantNotificationAdapter
import com.example.smartperty.tenant.notification.data.TenantNotification
import com.example.smartperty.tenant.notification.data.TenantNotificationList
import com.example.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_chat_main.view.*
import kotlinx.android.synthetic.main.tenant_fragment_notification_main.view.*

class LandlordChatFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_chat_main, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        val chatList = mutableListOf(
            LandlordChat(name = "Jason",message = "你好"),
            LandlordChat(name = "Aiden",message = "你好"),
            LandlordChat(name = "Hugo",message = "你好~~")
        )

        root.recycler_chat.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordChatAdapter(requireActivity(), root, chatList)
        }

        return root
    }
}
