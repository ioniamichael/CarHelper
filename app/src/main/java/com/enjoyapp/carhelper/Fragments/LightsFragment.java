package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public LightsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_lights, container, false);
            initView(view);
            presenter = new LightsView(lights, light, this);
            presenter.loadData();
        }
        return view;
    }

    @Override
    public void setAdapter() {
        lightsAdapter = new LightsAdapter(getActivity(), lights);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        RVLights.setLayoutManager(layoutManager);
        RVLights.setAdapter(lightsAdapter);
        lightsAdapter.setOnLightImageClickListener(new LightsAdapter.OnLightImageClickListener() {
            @Override
            public void onLightImageClick(int position) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.removeSortFragment();
            }
        });

    }

    public void sortAdapter() {
        Collections.sort(lights);
        lightsAdapter.updateData(lights);
    }

    public void initView(View view) {
        RVLights = view.findViewById(R.id.RVLights);
    }

}
