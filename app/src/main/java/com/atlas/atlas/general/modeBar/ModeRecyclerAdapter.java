package com.atlas.atlas.general.modeBar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atlas.atlas.R;

import java.util.ArrayList;
import java.util.List;


public class ModeRecyclerAdapter extends RecyclerView.Adapter<ModeBubbleViewHolder> {
    private byte type;
    private Context context;
    private int position = -1;
    private List<ModeBubbleScript> modes;

    public ModeRecyclerAdapter(byte type, Context context, int position) {
        this.type = type;
        this.context = context;
        this.position = position;

        modes = new ArrayList<>(5);
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));

        modes.add(new ModeBubbleScript(R.drawable.mode_friends));

        modes.add(new ModeBubbleScript(R.drawable.mode_friends));


        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        modes.add(new ModeBubbleScript(R.drawable.mode_friends));
        int internalPosition = position;
        switch (type){

            case ModeFragment.TYPE_CAM:
                internalPosition--;
                if(position == 0){
                    //the mode where you select the cam mode

                }

                case ModeFragment.TYPE_PREVIEW:

                    switch (internalPosition){
                        case 0:{
                            //the filterMOde

                        }
                        case 1:{
                            //the clockMode


                        }
                        case 2:{
                            //the textMode

                        }
                    }
        }
    }

    public ModeRecyclerAdapter(byte type, Context context) {
        this.type = type;
        this.context = context;
        //the select Layout

        modes = new ArrayList<>(5);
        modes.add(new ModeBubbleScript(R.drawable.mode_posts));

        modes.add(new ModeBubbleScript(R.drawable.mode_posts));

        modes.add(new ModeBubbleScript(R.drawable.mode_posts));

        modes.add(new ModeBubbleScript(R.drawable.mode_posts));


    }

    @Override
    public ModeBubbleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ModeBubbleViewHolder(LayoutInflater.from(context).inflate(R.layout.mode_bubble, parent, false));
    }

    @Override
    public void onBindViewHolder(ModeBubbleViewHolder holder, int position) {
        holder.getCircleImageView().setImageResource(modes.get(0).getResourceId());
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }
}
