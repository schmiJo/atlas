package com.atlas.atlas.general.modeBar

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.atlas.atlas.R

import de.hdodenhof.circleimageview.CircleImageView

class SelectModeBubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val circleImageView: CircleImageView = itemView.findViewById(R.id.modeImage) as CircleImageView
    val textView: TextView = itemView.findViewById(R.id.modeName) as TextView
    val selectView:View  = itemView.findViewById(R.id.mode_tick)
}
