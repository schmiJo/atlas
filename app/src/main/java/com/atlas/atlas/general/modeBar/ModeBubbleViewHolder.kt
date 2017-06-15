package com.atlas.atlas.general.modeBar

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.atlas.atlas.R

import de.hdodenhof.circleimageview.CircleImageView

class ModeBubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val circleImageView: CircleImageView
    val textView: TextView

    init {
        circleImageView = itemView.findViewById(R.id.modeImage) as CircleImageView
        textView = itemView.findViewById(R.id.modeName) as TextView
    }
}