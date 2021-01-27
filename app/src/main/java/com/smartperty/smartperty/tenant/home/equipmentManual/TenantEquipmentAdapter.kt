package com.smartperty.smartperty.tenant.home.equipmentManual

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Equipment
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_estate_directory_create.view.*
import kotlinx.android.synthetic.main.tenant_card_equipment.view.*
import kotlinx.android.synthetic.main.tenant_card_image_page.view.*
import kotlinx.android.synthetic.main.tenant_fragment_equipment_manual.view.*
import kotlinx.coroutines.GlobalScope

class TenantEquipmentAdapter(private val activity: Activity,
                             private val parentView: View,
                             private val myDataset: MutableList<Equipment>)
    : RecyclerView.Adapter<TenantEquipmentAdapter.CardHolder>() {

    lateinit var imageCard: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tenant_card_equipment, parent, false)

        imageCard = parentView.include_image_card
        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.title.text = myDataset[position].name
        holder.amount.text = myDataset[position].count.toString()

        holder.image.setImageBitmap(myDataset[position].image)
        holder.image.setOnClickListener {
            GlobalVariables.imageHelper.openLargeImage(holder.image.drawable.toBitmap())
        }

//        holder.cardView.setOnClickListener {
//            parentView.findNavController().navigate(
//                R.id.action_tenantEquipmentManualFragment_to_tenantEquipmentManualPdfFragment)
//        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        val title: TextView = card.text_equipment
        val amount: TextView = card.text_amount
        val image: ImageView = card.image_equipment
    }
}