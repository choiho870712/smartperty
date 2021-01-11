package com.smartperty.smartperty.repair

import android.app.Activity
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.smartperty.smartperty.R
import kotlinx.android.synthetic.main.card_image2.view.*

class ImageListAdapter2(private val activity: Activity,
                        private val parentView: View,
                        private val myDataset: MutableList<Bitmap>)
    : RecyclerView.Adapter<ImageListAdapter2.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_image2, parent, false)

        return CardHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.index = position
        holder.image.setImageBitmap(myDataset[position])
    }

    class CardHolder(card: View) : RecyclerView.ViewHolder(card) {
        val cardView: View = card
        var index: Int = -1
        val image: ImageView = card.imageView_card
    }
}