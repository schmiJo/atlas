package com.atlas.atlas.general.modeBar;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atlas.atlas.R;
import com.atlas.atlas.earth.EarthViewOptions;

import java.util.ArrayList;
import java.util.List;


 class EarthModeRecyclerAdapter extends RecyclerView.Adapter<SelectModeBubbleViewHolder> {

    private Context context;
    private List<SelectModeBubbleStruct> modes;


     EarthModeRecyclerAdapter(Context context) {
        this.context = context;

                SharedPreferences modePref = context.getSharedPreferences("userGeneralInfo", Context.MODE_PRIVATE);
                modes = new ArrayList<>(3);

                boolean friendsEnabled = modePref.getBoolean("friendsEnabled", true);
                modes.add(0, new SelectModeBubbleStruct(R.drawable.mode_friends, R.string.friends, friendsEnabled));
                EarthViewOptions.setFriendModeEnabled(friendsEnabled);

                boolean postsEnabled = modePref.getBoolean("postsEnabled", true);
                modes.add(1, new SelectModeBubbleStruct(R.drawable.mode_posts, R.string.posts, postsEnabled));
                EarthViewOptions.setPostModeEnabled(postsEnabled);

                boolean eventsEnabled = modePref.getBoolean("eventsEnabled", false);
                modes.add(2, new SelectModeBubbleStruct(R.drawable.mode_events, R.string.events, eventsEnabled));
                EarthViewOptions.setEventModeEnabled(eventsEnabled);



        }




    @Override
    public SelectModeBubbleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectModeBubbleViewHolder(LayoutInflater.from(context).inflate(R.layout.select_mode_bubble, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectModeBubbleViewHolder holder, int position) {
        SelectModeBubbleStruct mode = modes.get(position);
        holder.getCircleImageView().setImageResource(mode.getDrawableId());
        holder.getTextView().setText(mode.getStringId());
        if(mode.isSelected()){
            holder.getSelectView().setVisibility(View.VISIBLE);
        }else{
            holder.getSelectView().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }

    void selectItem(int position){
        modes.get(position).setSelected(true);
    }
    void unselectItem(int position){
        modes.get(position).setSelected(false);
    }
}
