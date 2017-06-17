package com.atlas.atlas.general.modeBar;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlas.atlas.R;
import com.atlas.atlas.camera.CameraFragment;
import com.atlas.atlas.general.RecyclerTouchListener;
import com.atlas.atlas.general.Utils;

/**
 * The camera mode bar that is used in the camera fragment and the preview actvity
 */
public class CameraModeFragment extends ModeFragment {


    private FrameLayout recycler2Layout;
    private RecyclerView recyclerView2;
    private RecyclerTouchListener recyclerTouchListener2;
    private CloseListener closeListener;
    private TextView modeRecyclerText;
    private int layerPosition = 0;
    private RecyclerView.OnScrollListener recycler2ScrollListner;
    private boolean downOnClick = true, ignoreScroll = false;
    private float dp54;
    private ModeChangeListener modeChangeListener;

    public CameraModeFragment() {
        // Required empty public constructor
    }

    public static CameraModeFragment getInstance(byte type) {

        if (type != TYPE_CAM && type != TYPE_PREVIEW) throw new RuntimeException("Type Exception");
        CameraModeFragment modeFragment = new CameraModeFragment();
        Bundle arguments = new Bundle();
        arguments.putByte(TYPE, type);
        modeFragment.setArguments(arguments);
        return modeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_mode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        layout = view;
        layer = 1;
        dp54 = Utils.dpToPixel(54, getActivity());
        type = getArguments().getByte(TYPE);
        modeChangeListener = (ModeChangeListener) getActivity();
        closeListener = (CloseListener) getActivity();
        imageView = (ImageView) view.findViewById(R.id.modeButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDownLayer();
            }
        });
        modeRecyclerText = (TextView) view.findViewById(R.id.modeRecyclerText);
        View centerCircle = view.findViewById(R.id.modeCenterCircle);
        centerCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    recyclerView2.onTouchEvent(event);
                    return true;
                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP && downOnClick)
                    goToLayer1from2();


                return recyclerView2.onTouchEvent(event);
            }
        });

        final ModeRecyclerAdapter recyclerAdapter = new ModeRecyclerAdapter(type, getActivity());
        recyclerView = (RecyclerView) layout.findViewById(R.id.modeRecycler);
        recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position, MotionEvent e) {
                layerPosition = position;

                if (recyclerView2 == null) {
                    createRecycler2();
                }
                if (recycler2Layout == null) {
                    recycler2Layout = (FrameLayout) layout.findViewById(R.id.recycler2Layout);
                }

                layer = 2;
                ModeRecyclerAdapter adapter = new ModeRecyclerAdapter(TYPE_CAM, getActivity(), position);
                recyclerView2.setAdapter(adapter);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                switch (layerPosition) {
                    case 0:
                        recyclerView2.scrollToPosition(CameraFragment.MODE_CAM);
                        modeSetteled(CameraFragment.MODE_CAM);
                        break;
                    case 1:

                        modeSetteled(CameraFragment.MODE_FILTER);
                        recyclerView2.scrollToPosition(CameraFragment.MODE_FILTER);
                        break;
                    case 2:
                        modeSetteled(CameraFragment.MODE_TIME);
                        recyclerView2.scrollToPosition(CameraFragment.MODE_TIME);
                        break;
                    case 3:
                        modeSetteled(CameraFragment.MODE_TEXT);
                        recyclerView2.scrollToPosition(CameraFragment.MODE_TEXT);
                        break;
                }
                ignoreScroll = true;
                recyclerTouchListener2 = new RecyclerTouchListener(getActivity(), recyclerView2, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position, MotionEvent e) {

                        recyclerView2.smoothScrollToPosition(position);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                });

                recyclerView2.addOnItemTouchListener(recyclerTouchListener2);

                recycler2Layout.setVisibility(View.VISIBLE);
                recycler2Layout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_in_bottom));
                recyclerView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_out_top));
                recyclerView.setVisibility(View.INVISIBLE);
                imageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_out_top));
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        recyclerView.addOnItemTouchListener(recyclerTouchListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recyclerAdapter);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.modeCenterCircle && !downOnClick) return;
                goDownLayer();

            }
        };

        startStartAnim();
        centerCircle.setOnClickListener(onClickListener);

    }


    @Override
    public void goDownLayer() {

        if (layer == 1) {
            closeListener.onClose();
            layout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_out_bottom));
            layout.setVisibility(View.INVISIBLE);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeListener.onClosed();
                }
            }, 250);

        } else if (layer == 2) {

            goToLayer1from2();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerView2 != null && recyclerTouchListener2 != null) {
            recyclerView2.removeOnItemTouchListener(recyclerTouchListener2);
            recyclerView2.removeOnScrollListener(recycler2ScrollListner);
        }

    }

    public void startStartAnim() {
        layout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_in_bottom));
    }

    private void modeSetteled(int position) {
        downOnClick = true;
        switch (layerPosition) {
            case 0: {

                {
                    SharedPreferences camPref = getActivity().getSharedPreferences("userGeneralInfo", Context.MODE_PRIVATE);
                    camPref.edit().putInt("camMode", position).apply();

                }
                modeChangeListener.camModeChanged(position);
                CameraFragment.MODE_CAM = (byte) position;
                switch (position) {
                    case 0:

                        modeRecyclerText.setText(R.string.pictures);
                        break;
                    case 1:
                        modeRecyclerText.setText(R.string.video);
                        break;
                }

                break;
            }
            case 1: {

                {
                    SharedPreferences camPref = getActivity().getSharedPreferences("userGeneralInfo", Context.MODE_PRIVATE);
                    camPref.edit().putInt("filterMode", position).apply();


                }
                CameraFragment.MODE_FILTER = (byte) position;
                modeChangeListener.filterModeChanged(position);
                switch (position) {
                    case 0:

                        modeRecyclerText.setText(R.string.noFilter);
                        break;
                    case 1:
                        modeRecyclerText.setText(R.string.blackNWhite);
                        break;
                    case 2:
                        modeRecyclerText.setText(R.string.sepia);
                        break;

                }
                break;
            }
            case 2:


                CameraFragment.MODE_TIME = (byte) position;
                modeChangeListener.timeModeChanged(position);
                switch (position) {
                    case 0:

                        modeRecyclerText.setText(R.string.noTime);
                        break;
                    case 1:
                        modeRecyclerText.setText(R.string.clock);
                        break;
                    case 2:
                        modeRecyclerText.setText(R.string.watch);
                        break;
                }

                break;
            case 3:

                CameraFragment.MODE_TEXT = (byte) position;
                modeChangeListener.textModeChanged(position);
                switch (position) {
                    case 0:
                        modeRecyclerText.setText(R.string.noText);
                        break;
                    case 1:
                        modeRecyclerText.setText(R.string.text);
                        break;
                    case 2:
                        modeRecyclerText.setText(R.string.speechBubble);
                        break;
                }
                break;

        }
    }

    private void goToLayer1from2() {
        layer = 1;
        if (recyclerView2 == null) {
            createRecycler2();
        }
        if (recycler2Layout == null) {
            recycler2Layout = (FrameLayout) layout.findViewById(R.id.recycler2Layout);
        }
        recycler2Layout.setVisibility(View.INVISIBLE);
        recycler2Layout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_out_bottom));
        recyclerView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_in_top));
        recyclerView.setVisibility(View.VISIBLE);
        imageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.modes_slide_in_top));
        imageView.setVisibility(View.VISIBLE);
        recyclerView2.removeOnItemTouchListener(recyclerTouchListener2);

    }

    private void createRecycler2() {
        downOnClick = true;
        recyclerView2 = (RecyclerView) layout.findViewById(R.id.modeRecycler2);
        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView2);


        recycler2ScrollListner = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView2.computeHorizontalScrollOffset() == 0) {
                    modeSetteled(0);
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    modeSetteled(Math.round((float) recyclerView2.computeHorizontalScrollOffset() / dp54));
                } else {
                    modeRecyclerText.setText("");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!ignoreScroll) {
                    downOnClick = false;

                }else{
                ignoreScroll = false;
            }}
        };

        recyclerView2.addOnScrollListener(recycler2ScrollListner);


        int padding = recyclerView2.getWidth() / 2 - (int) Utils.dpToPixel(27, getActivity());

        recyclerView2.setPadding(padding, 0, padding, 0);

    }

    public interface CloseListener {
        void onClosed();

        void onClose();
    }

    public interface ModeChangeListener {
        void camModeChanged(int position);

        void filterModeChanged(int position);

        void timeModeChanged(int position);

        void textModeChanged(int position);
    }


}
