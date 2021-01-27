package com.smartperty.smartperty.landlord.menu.estate

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.igreenwood.loupe.Loupe
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.card_estate_directory.view.*
import kotlinx.android.synthetic.main.card_estate_directory_menu.view.*
import kotlinx.android.synthetic.main.fragment_estate_directory.view.*

class EstateDirectoryAdapter(private val activity: Activity,
                             private val parentView: View,
                             private val myDataset: MutableList<EstateList>)
    : RecyclerView.Adapter<EstateDirectoryAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_estate_directory, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.title.text = myDataset[position].title
        holder.squareFt.text = myDataset[position].calculateSquareFt().toString()
        holder.itemNumber.text = myDataset[position].list.size.toString()
        holder.rentRate.text = myDataset[position].calculateRentRate().toString()

        if(myDataset[position].image != null)
            holder.image.setImageBitmap(myDataset[position].image)
        else
            holder.image.setImageDrawable(
                activity.resources.getDrawable(R.drawable.empty_house))

        holder.image.setOnClickListener {
            GlobalVariables.imageHelper.openLargeImage(holder.image.drawable.toBitmap())
        }

        holder.cardView.setOnClickListener {
            GlobalVariables.estateFolder = myDataset[holder.index]
            parentView.findNavController().navigate(
                R.id.action_landlordObjectFolderFragment_to_landlordObjectListFragment)
        }

//        holder.image.setOnClickListener {
//            parentView.imageView_large.setImageDrawable(holder.image.drawable)
//            parentView.card_image_large.visibility = View.VISIBLE
//        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val title: TextView = card.text_object_folder_title
        val squareFt: TextView = card.text_object_folder_square_ft
        val itemNumber: TextView = card.text_object_folder_item_number
        val rentRate: TextView = card.text_object_folder_rent_rate
        val image: ImageView = card.image_object_folder
    }
}
