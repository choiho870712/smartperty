package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.UserInfo
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_personnel_list.view.*

class PersonnelListAdapter(private val activity: Activity,
                           private val parentView: View,
                           private val myDataset: MutableList<UserInfo>)
    : RecyclerView.Adapter<PersonnelListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_personnel_list, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.name.text = myDataset[position].name

        holder.cardView.setOnClickListener {
            GlobalVariables.personnel = myDataset[holder.index]
            parentView.findNavController().navigate(
                R.id.action_personnelListFragment_to_personnelFragment)
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val name: TextView = card.text_personnel_list_name
    }
}