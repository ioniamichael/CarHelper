package com.enjoyapp.carhelper.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.enjoyapp.carhelper.Adapters.LightsAdapter;
import com.enjoyapp.carhelper.Models.Light;
import com.enjoyapp.carhelper.Screens.MainActivity;
import com.enjoyapp.carhelper.Views.LightsView;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Presenters.LightsPresenter;

import java.util.ArrayList;
import java.util.Collections;

public class LightsFragment extends Fragment implements LightsPresenter {

    private RecyclerView RVLights;
    private ArrayList<Light> lights = new ArrayList<>();
    private Light light;
    private LightsAdapter lightsAdapter;
    private LightsView presenter;
    private View view;
    private Animation animation;

    public LightsFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            initView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_lights, container, false);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new LightsView(lights, light, this);
        presenter.loadData();
    }

    //Setting adapter parameters.
    @Override
    public void setAdapter() {
        lightsAdapter = new LightsAdapter(getActivity(), lights);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        RVLights.setLayoutManager(layoutManager);
        RVLights.setAdapter(lightsAdapter);
        lightsAdapter.setOnLightImageTouchListener(new LightsAdapter.OnLightImageTouchListener() {
            @Override
            public void onLightImageTouch(final int position, MotionEvent event, LightsAdapter.LightsViewHolder holder) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                        holder.itemView.startAnimation(animation);
                        Log.d("IsTouched", "positionTouchedDown " + position);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.removeSortFragment();
                        break;
                    case MotionEvent.ACTION_UP:
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lightsAdapter.openLightInfo(position);
                                lightsAdapter.notifyDataSetChanged();
                            }
                        }, 100);
                        Log.d("IsTouched", "positionTouchedUP" + position);
                        break;
                }
            }
        });
    }

    //Sorting adapter by warning color ( 1-4, red - blue )
    public void sortAdapter() {
        Collections.sort(lights);
        lightsAdapter.updateData(lights);
    }

    //Initializing views.
    public void initView(View view) {
        RVLights = view.findViewById(R.id.RVLights);
    }
}