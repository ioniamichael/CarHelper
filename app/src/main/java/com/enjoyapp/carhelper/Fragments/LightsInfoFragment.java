package com.enjoyapp.carhelper.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.enjoyapp.carhelper.Adapters.LightsAdapter;
import com.enjoyapp.carhelper.Models.Light;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Views.LightsView;

import java.util.ArrayList;

public class LightsInfoFragment extends Fragment {
    private TextView testTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights_info, container, false);
        testTextView = view.findViewById(R.id.testTextView);

        return view;
    }

}