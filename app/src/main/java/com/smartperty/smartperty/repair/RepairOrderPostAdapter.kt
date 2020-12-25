package com.smartperty.smartperty.repair

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.RepairOrderPost
import kotlinx.android.synthetic.main.card_repair_order_post.view.*

class RepairOrderPostAdapter(private val activity: Activity,
                             private val parentView: View,
                             private val myDataset: MutableList<RepairOrderPost>)
    : RecyclerView.Adapter<RepairOrderPostAdapter.CardHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_repair_order_post, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.sender.text = myDataset[position].sender!!.name
        holder.message.text = myDataset[position].message
        holder.date.text = myDataset[position].createDateTime
        holder.imageListAdapter = ImageListAdapter(activity, parentView,
            myDataset[position].imageList)
        holder.imageList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = holder.imageListAdapter
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val sender: TextView = card.textView_repair_order_post_sender
        val message: TextView = card.textView_repair_order_post_message
        var date: TextView = card.textView_repair_order_post_date_time
        val imageList: RecyclerView = card.recyclerView_image
        lateinit var imageListAdapter: ImageListAdapter
    }
}