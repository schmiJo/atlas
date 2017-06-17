package com.atlas.atlas.general.modeBar

import android.view.View
import android.widget.TextView
import com.atlas.atlas.R


class ModeTextBubbleViewHolder(itemView: View):ModeBubbleViewHolder(itemView) {
val textView: TextView = itemView.findViewById(R.id.modeRecyclerText)as TextView;
}