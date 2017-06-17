package com.atlas.atlas.general.modeBar;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atlas.atlas.R;
import com.atlas.atlas.general.RecyclerTouchListener;
import com.atlas.atlas.general.Utils;

/**
 * Always used when there is a need for modes or selection between different items
 */
public class ModeFragment extends Fragment {


    public static final String TYPE = "TYPE";
    public static final byte TYPE_EARTH = 0;
    public static final byte TYPE_CAM = 1;
    public static final byte TYPE_PREVIEW = 2;
    public static final byte TYPE_CHAT = 3;
    public byte layer = 0;
    protected byte type;
    protected RecyclerView recyclerView;
    protected FrameLayout blurView;
    protected ImageView imageView;
    protected FrameLayout imageLayout;
    protected View layout;
    protected RecyclerTouchListener recyclerTouchListener;

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

//this method does not apply for type_cam and type_preview
        layout = view;

        type = getArguments().getByte(TYPE);

        imageView = (ImageView) view.findViewById(R.id.modeButton);
        blurView = (FrameLayout) view.findViewById(R.id.modeBlurView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case TYPE_EARTH:
                        if (recyclerView == null || recyclerTouchListener == null) {
                            final EarthModeRecyclerAdapter adapter = new EarthModeRecyclerAdapter(getActivity());
                            recyclerView = (RecyclerView) layout.findViewById(R.id.modeRecycler);
                            recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position, MotionEvent e) {
                                    switch (type) {
                                        case TYPE_EARTH: {
                                            View tick = view.findViewById(R.id.mode_tick);

                                            SharedPreferences modePref = getActivity().getSharedPreferences("userGeneralInfo", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor edit = modePref.edit();
                                            String modeString;
                                            switch (position) {
                                                case 0:
                                                    modeString = "friendsEnabled";
                                                    break;
                                                case 1:
                                                    modeString = "postsEnabled";
                                                    break;
                                                default:
                                                    modeString = "eventsEnabled";
                                                    break;

                                            }
                                            if (tick.getVisibility() == View.VISIBLE) {

                                                tick.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.mode_indicator_fade_out));
                                                tick.setVisibility(View.INVISIBLE);
                                                adapter.unselectItem(position);
                                                edit.putBoolean(modeString, false);
                                            } else {
                                                tick.setVisibility(View.VISIBLE);
                                                tick.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.mode_indicator_fade_in));
                                                adapter.selectItem(position);

                                                edit.putBoolean(modeString, true);

                                            }
                                            edit.apply();

                                        }
                                    }

                                }


                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            });
                            recyclerView.addOnItemTouchListener(recyclerTouchListener);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                            recyclerView.setAdapter(adapter);
                        }


                        if (layer == 1) {
                         goDownLayer();
                        } else {

                            blurView.setVisibility(View.VISIBLE);
                            blurView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_in_top));
                            layer = 1;


                            int dp28 = (int) Utils.dpToPixel(42, getActivity());
                            imageView.getLayoutParams().height = dp28;
                            imageView.getLayoutParams().width = dp28;

                            imageView.setImageResource(R.drawable.animated_mode_arrow);

                            Drawable drawable = imageView.getDrawable();
                            if (drawable instanceof Animatable) {
                                ((Animatable) drawable).start();
                            }
                            if (imageLayout == null)
                                imageLayout = (FrameLayout) layout.findViewById(R.id.modeImageLayout);

                            ChangeBounds changeBounds = new ChangeBounds();
                            changeBounds.setDuration(200);
                            TransitionManager.beginDelayedTransition(imageLayout, changeBounds);

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
        if (recyclerView != null && recyclerTouchListener != null) {
            recyclerView.removeOnItemTouchListener(recyclerTouchListener);
        }
    }


    public void goDownLayer(){
        switch (type) {
            case TYPE_EARTH: {
                blurView.setVisibility(View.INVISIBLE);
                blurView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
                recyclerView.setVisibility(View.INVISIBLE);
                recyclerView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_out_top));

                int dp28 = (int) Utils.dpToPixel(36, getActivity());
                imageView.getLayoutParams().height = dp28;

                imageView.getLayoutParams().width = dp28;
                imageView.setImageResource(R.drawable.animated_mode_arrow_close);
                layer = 0;
                Drawable drawable = imageView.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                if (imageLayout == null)
                    imageLayout = (FrameLayout) layout.findViewById(R.id.modeImageLayout);

                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setDuration(200);
                TransitionManager.beginDelayedTransition(imageLayout, changeBounds);

            }
        }
    }

}
