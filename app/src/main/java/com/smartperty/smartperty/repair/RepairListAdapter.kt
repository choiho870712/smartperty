package com.smartperty.smartperty.repair

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.RepairOrder
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_repair_list.view.*

class RepairListAdapter(private val activity: Activity,
                        private val parentView: View,
                        private val myDataset: MutableList<RepairOrder>)
    : RecyclerView.Adapter<RepairListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_repair_list, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.address.text = myDataset[position].estate?.address ?: ""
        holder.title.text = myDataset[position].title
//        holder.status.text = RepairStatus.getStringByStatus(myDataset[position].status)
        holder.status.text = myDataset[position].statusString

        if (myDataset[position].postList.isNotEmpty()) {
            holder.date.text = myDataset[position].postList[0].createDateTime
            if (myDataset[position].postList[0].imageList.isNotEmpty())
                holder.image.setImageBitmap(myDataset[position].postList[0].imageList[0])
            else
                holder.image.setImageDrawable(
                    activity.resources.getDrawable(R.drawable.ic_empty_house))
        }
        else {
            holder.date.text = ""
            holder.image.setImageDrawable(
                activity.resources.getDrawable(R.drawable.ic_empty_house))
        }

        holder.cardView.setOnClickListener {
            GlobalVariables.repairOrder = myDataset[holder.index]
            if (parentView.findNavController().currentDestination?.id == R.id.repairListFragment)
                parentView.findNavController().navigate(
                    R.id.action_repairListFragment_to_repairOrderFragment)
            if (parentView.findNavController().currentDestination?.id == R.id.estateFragment)
                parentView.findNavController().navigate(
                    R.id.action_estateFragment_to_repairOrderFragment)

        }

    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val address: TextView = card.text_repair_list_address
        val title: TextView = card.text_repair_list_title
        val date: TextView = card.text_repair_list_date
        val image: ImageView = card.image_repair_list
        val status: TextView = card.text_repair_list_status
    }
}