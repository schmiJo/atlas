package com.atlas.atlas.general.modeBar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atlas.atlas.R;

import java.util.ArrayList;
import java.util.List;


public class ModeRecyclerAdapter extends RecyclerView.Adapter<ModeBubbleViewHolder> {

    private Context context;
    private List<ModeBubbleStruct> modes;
    private byte type;

    public ModeRecyclerAdapter(Context context, byte type) {
        this.context = context;
        this.type = type;

        switch (type) {
            case ModeFragment.TYPE_EARTH:
                modes = new ArrayList<>(3);
                modes.add(0, new ModeBubbleStruct(R.drawable.mode_friends, R.string.friends,false));
                modes.add(1, new ModeBubbleStruct(R.drawable.mode_posts, R.string.posts, false));
                modes.add(2, new ModeBubbleStruct(R.drawable.mode_events, R.string.events, false));
                break;

            default: modes = new ArrayList<>(0);



        }
    }

    @Override
    public ModeBubbleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ModeBubbleViewHolder(LayoutInflater.from(context).inflate(R.layout.mode_bubble, parent, false));
    }

    @Override
    public void onBindViewHolder(ModeBubbleViewHolder holder, int position) {
        ModeBubbleStruct mode = modes.get(position);
        holder.getCircleImageView().setImageResource(mode.getDrawableId());
        holder.getTextView().setText(mode.getStringId());
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }
}
