package com.smartperty.smartperty.tools

import android.R
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.annotations.NonNull


abstract class SwipeToDeleteCallback internal constructor(context: Context) :
    ItemTouchHelper.Callback() {
    var mContext: Context = context
    private val mClearPaint: Paint = Paint()
    private val mBackground: ColorDrawable = ColorDrawable()
    private val backgroundColor: Int = Color.parseColor("#b80f0a")
    private val deleteDrawable: Drawable?
    private val intrinsicWidth: Int
    private val intrinsicHeight: Int
    override fun getMovementFlags(
        @NonNull recyclerView: RecyclerView,
        @NonNull viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        @NonNull recyclerView: RecyclerView,
        @NonNull viewHolder: RecyclerView.ViewHolder,
        @NonNull viewHolder1: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView
        val itemHeight: Int = itemView.getHeight()
        val isCancelled = dX == 0f && !isCurrentlyActive
        if (isCancelled) {
            clearCanvas(
                c,
                itemView.getRight() + dX,
                itemView.getTop().toFloat(),
                itemView.getRight().toFloat(),
                itemView.getBottom().toFloat()
            )
            super.onChildDraw(
                c,
                recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
            )
            return
        }
        mBackground.color = backgroundColor
        mBackground.setBounds(
            itemView.getRight() + dX.toInt(),
            itemView.getTop(),
            itemView.getRight(),
            itemView.getBottom()
        )
        mBackground.draw(c)
        val deleteIconTop: Int = itemView.getTop() + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft: Int = itemView.getRight() - deleteIconMargin - intrinsicWidth
        val deleteIconRight: Int = itemView.getRight() - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight
        deleteDrawable!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteDrawable.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, mClearPaint)
    }

    override fun getSwipeThreshold(@NonNull viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    init {
        mClearPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        deleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_delete)
        intrinsicWidth = deleteDrawable!!.intrinsicWidth
        intrinsicHeight = deleteDrawable.intrinsicHeight
    }
}