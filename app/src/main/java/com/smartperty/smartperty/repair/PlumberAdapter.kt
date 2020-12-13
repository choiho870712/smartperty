package com.smartperty.smartperty.repair

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_plumber.view.*
import kotlinx.android.synthetic.main.fragment_choose_plumber.view.*

class PlumberAdapter(private val activity: Activity,
                     private val parentView: View,
                     private val myDataset: MutableList<User>)
    : RecyclerView.Adapter<PlumberAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_plumber, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.name.text = myDataset[position].name
        if (myDataset[position].icon != null)
            holder.image.setImageBitmap(myDataset[position].icon)

        holder.cardView.setOnClickListener {
            GlobalVariables.repairOrder.plumber = myDataset[holder.index]
            GlobalVariables.plumber = myDataset[holder.index]
            parentView.textView_choose_plumber_name.text = GlobalVariables.plumber.name
            parentView.textView_choose_plumber_cell_phone.text = GlobalVariables.plumber.cellPhone
            parentView.textView_choose_plumber_company.text = GlobalVariables.plumber.company
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val name: TextView = card.text_name
        val image: ImageView = card.image
    }
}