package com.example.smartperty.landlord.menu.`object`.objectFolder

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartperty.R
import com.example.smartperty.landlord.menu.`object`.data.LandlordObjectList
import kotlinx.android.synthetic.main.landlord_card_object_folder.view.*

class LandlordObjectFolderAdapter(private val activity: Activity,
                                  private val parentView: View,
                                  private val myDataset: MutableList<LandlordObjectList>)
    : RecyclerView.Adapter<LandlordObjectFolderAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.landlord_card_object_folder, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].title

        holder.cardView.setOnClickListener {
            parentView.findNavController().navigate(
                R.id.action_landlordObjectFolderFragment_to_landlordObjectListFragment)
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_object_folder_title
    }
}