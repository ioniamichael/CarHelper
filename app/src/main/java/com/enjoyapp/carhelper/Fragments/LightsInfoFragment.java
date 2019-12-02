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

    private String mLightsTitle;
    private String mLightsDesc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mLightsTitle = getArguments().getString("LIGHTS_TITLE");
        mLightsDesc = getArguments().getString("LIGHTS_DESC");
    }

    private TextView mLightTitle, mLightDesc, mLightType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights_info, container, false);
        mLightTitle = view.findViewById(R.id.lightTitle);
        mLightDesc = view.findViewById(R.id.lightDesc);
        mLightType = view.findViewById(R.id.lightType);
        showFragment();
        return view;
    }

    private void showFragment() {
        mLightTitle.setText(mLightsTitle);
        mLightDesc.setText(mLightsDesc);
    }



}
