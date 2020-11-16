package com.example.smartperty.tenant.home.equipmentManual

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.smartperty.R
import kotlinx.android.synthetic.main.tenant_section_equipment.view.*
import java.util.*


class TenantEquipmentSectionedAdapter(
    private val baseAdapter: RecyclerView.Adapter<TenantEquipmentAdapter.CardHolder>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    private var isValid = true
    private val sections = SparseArray<Section?>()

    override fun onCreateViewHolder(parent: ViewGroup, typeView: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if (typeView == SECTION_TYPE) {
            val view = LayoutInflater.from(context).inflate(R.layout.tenant_section_equipment, parent, false)
            SectionViewHolder(view)
        } else {
            baseAdapter.onCreateViewHolder(parent, typeView - 1)
        }
    }

    override fun getItemCount(): Int = if (isValid) baseAdapter.itemCount + sections.size() else 0

    override fun onBindViewHolder(sectionViewHolder: RecyclerView.ViewHolder, position: Int) {
        if (isSectionHeaderPosition(position)) {
            (sectionViewHolder as SectionViewHolder).title.text =
                sections[position]!!.title
        } else {
            baseAdapter.onBindViewHolder(
                sectionViewHolder as TenantEquipmentAdapter.CardHolder,
                sectionedPositionToPosition(position))
        }
    }

    class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.text_sectioned_title_equipment
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSectionHeaderPosition(position)) SECTION_TYPE else baseAdapter.getItemViewType(
            sectionedPositionToPosition(position)
        ) + 1
    }

    class Section(var firstPosition: Int, var title: CharSequence) {
        var sectionedPosition = 0

    }

    fun setSections(sections: Array<Section>) {
        this.sections.clear()
        Arrays.sort(sections
        ) { o, o1 -> if (o.firstPosition == o1.firstPosition) 0 else if (o.firstPosition < o1.firstPosition) -1 else 1 }
        for ((offset, section) in sections.withIndex()) {
            section.sectionedPosition = section.firstPosition + offset
            this.sections.append(section.sectionedPosition, section)
        }
        notifyDataSetChanged()
    }

    fun positionToSectionedPosition(position: Int): Int {
        var offset = 0
        for (i in 0 until sections.size()) {
            if (sections.valueAt(i)!!.firstPosition > position) {
                break
            }
            ++offset
        }
        return position + offset
    }

    fun sectionedPositionToPosition(sectionedPosition: Int): Int {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION
        }
        var offset = 0
        for (i in 0 until sections.size()) {
            if (sections.valueAt(i)!!.sectionedPosition > sectionedPosition) {
                break
            }
            --offset
        }
        return sectionedPosition + offset
    }

    fun isSectionHeaderPosition(position: Int): Boolean {
        return sections[position] != null
    }

    override fun getItemId(position: Int): Long {
        return if (isSectionHeaderPosition(position))
            (Int.MAX_VALUE - sections.indexOfKey(position)).toLong() else baseAdapter.getItemId(
            sectionedPositionToPosition(position)
        )
    }

    companion object {
        private const val SECTION_TYPE = 0
    }

    init {
        baseAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                isValid = baseAdapter.itemCount > 0
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                isValid = baseAdapter.itemCount > 0
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                isValid = baseAdapter.itemCount > 0
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                isValid = baseAdapter.itemCount > 0
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })
    }
}