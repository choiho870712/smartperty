package com.smartperty.smartperty.landlord.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.chat.data.LandlordChat
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_chat_main.view.*

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
