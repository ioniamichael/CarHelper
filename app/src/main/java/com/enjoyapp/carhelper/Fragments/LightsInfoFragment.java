package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.R;


public class LightsInfoFragment extends Fragment {

    private String lightsTitle;
    private String lightsDesc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightsTitle = getArguments().getString("LIGHTS_TITLE");
        lightsDesc = getArguments().getString("LIGHTS_DESC");
    }

    private TextView lightTitle, lightDesc, lightType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights_info, container, false);
        lightTitle = view.findViewById(R.id.lightTitle);
        lightDesc = view.findViewById(R.id.lightDesc);
        lightType = view.findViewById(R.id.lightType);
        showFragment();
        return view;
    }

    public void showFragment() {
        lightTitle.setText(lightsTitle);
        lightDesc.setText(lightsDesc);
    }

}
