package com.atlas.atlas.general.modeBar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atlas.atlas.R;
import com.atlas.atlas.camera.CameraFragment;

import java.util.ArrayList;
import java.util.List;


class ModeRecyclerAdapter extends RecyclerView.Adapter<ModeBubbleViewHolder> {
    private byte type;
    private Context context;
    private int position = -1;
    private List<ModeBubbleScript> modes;

    private boolean text;


    ModeRecyclerAdapter(byte type, Context context, int position) {
        this.type = type;
        this.context = context;
        this.position = position;
        text = false;
        modes = new ArrayList<>(5);
        switch (position) {
            case 0: {
                //the filterMOde
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_nofilter));

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_blacknwhite));
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_sepia));


                break;

            }
            case 1: {
                //the clockMode
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_notime));
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_clock));
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_watch));

                break;

            }
            case 2: {
                //the textMode
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_notext));
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_text));
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_clock));
            }
        }

    }

    ModeRecyclerAdapter(byte type, Context context) {
        this.type = type;
        this.context = context;
        text = true;
        //the select Layout

        modes = new ArrayList<>(4);


        switch (CameraFragment.MODE_FILTER) {
            case 0:

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_nofilter));
                break;
            case 1:

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_blacknwhite));
                break;
            case 2:

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_sepia));
                break;
        }

        switch (CameraFragment.MODE_TIME) {
            case 0:
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_notime));
                break;
            case 1:

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_clock));
                break;
            case 2:

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_watch));
                break;
        }

        switch (CameraFragment.MODE_TEXT) {
            case 0:
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_notext));
                break;
            case 1:
                modes.add(new ModeBubbleScript(R.drawable.mode_cam_text));

                break;
            case 2:

                modes.add(new ModeBubbleScript(R.drawable.mode_cam_clock));
                break;

        }


    }

    private static int getText(int position) {
        switch (position) {
            case 0:
                //filter modes
                return R.string.filter;
            case 1:
                //time modes
                return R.string.time;
            case 2:
                //text modes
                return R.string.text;

        }

        return R.string.noFilter;
    }

    @Override
    public ModeBubbleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (text)
            return new ModeTextBubbleViewHolder(LayoutInflater.from(context).inflate(R.layout.mode_bubble_text, parent, false));
        return new ModeBubbleViewHolder(LayoutInflater.from(context).inflate(R.layout.mode_bubble, parent, false));
    }

    @Override
    public void onBindViewHolder(ModeBubbleViewHolder holder, int position) {
        holder.getCircleImageView().setImageResource(modes.get(position).getResourceId());
        if (text) ((ModeTextBubbleViewHolder) holder).getTextView().setText(getText(position));

    }

    @Override
    public int getItemCount() {
        return modes.size();
    }
}
