package com.atlas.atlas.general.modeBar;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atlas.atlas.R;
import com.wonderkiln.blurkit.BlurLayout;

/**
 * Always used when there is a need for modes or selection between different items
 */
public class ModeFragment extends Fragment {


    public static final String TYPE = "TYPE";
    public static final byte TYPE_EARTH = 0;
    public static final byte TYPE_CAM = 1;
    public static final byte TYPE_PREVIEW = 2;
    public static final byte TYPE_CHAT = 3;


    private byte type;
    private RecyclerView recyclerView;
    private FrameLayout blurView;
    private ImageView imageView;

    private byte layer = 0;

    public ModeFragment() {
    }

    public static ModeFragment getInstance(byte type) {

        ModeFragment modeFragment = new ModeFragment();
        Bundle arguments = new Bundle();
        arguments.putByte(TYPE, type);
        modeFragment.setArguments(arguments);
        return modeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        type = getArguments().getByte(TYPE);
        recyclerView = (RecyclerView) view.findViewById(R.id.modeRecycler);
        imageView = (ImageView) view.findViewById(R.id.modeButton);
        blurView = (FrameLayout) view.findViewById(R.id.modeBlurView);

        recyclerView.setAdapter(new ModeRecyclerAdapter(getActivity(), type));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case TYPE_EARTH:
                    case TYPE_CHAT:
                        if(layer == 1) {
                            blurView.setVisibility(View.GONE);
                            blurView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_out_top));
                            layer = 0;
                        }else{
                            blurView.setVisibility(View.VISIBLE);
                            blurView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_in_top));
                            layer = 1;
                        }

                        break;
                }
            }
        });



    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
