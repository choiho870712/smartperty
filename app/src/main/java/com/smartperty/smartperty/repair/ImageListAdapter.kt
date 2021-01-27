package com.smartperty.smartperty.repair

import android.app.Activity
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.card_image.view.*
import kotlinx.android.synthetic.main.fragment_estate_directory_create.view.*

class ImageListAdapter(private val activity: Activity,
                       private val parentView: View,
                       private val myDataset: MutableList<Bitmap>)
    : RecyclerView.Adapter<ImageListAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_image, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.image.setImageBitmap(myDataset[position])

        holder.image.setOnClickListener {
            GlobalVariables.imageHelper.openLargeImage(holder.image.drawable.toBitmap())
        }

        if (GlobalVariables.imageListUsage == "edit") {
            holder.removeButton.visibility = View.VISIBLE
            holder.removeButton.setOnClickListener {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(activity)
                builder.setTitle("確定要刪除嗎？")

                builder.setPositiveButton("是") { _, _ ->

                    var notRemovedCount = 0
                    GlobalVariables.imageEditIndexLog.forEachIndexed { index, isRemoved ->
                        if (!isRemoved) {
                            if (position == notRemovedCount) {
                                GlobalVariables.imageEditIndexLog[index] = true
                            }
                            notRemovedCount++
                        }
                    }
                    myDataset.remove(myDataset[position])
                    notifyDataSetChanged()
                }

                // create dialog and show it
                activity.runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
        else {
            holder.removeButton.visibility = View.GONE
        }
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val image: ImageView = card.imageView_card
        val removeButton: ImageView = card.button_remove
    }
}