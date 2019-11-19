package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.Adapters.GaragesAdapter;
import com.enjoyapp.carhelper.Models.Garage;
import com.enjoyapp.carhelper.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GarageListFragment extends Fragment {

    private RecyclerView RVGarage;
    private ArrayList<Garage> garages;
    private Garage garage;
    private GaragesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage_list, container, false);
        readData();
        RVGarage = view.findViewById(R.id.RVGarage);
        adapter = new GaragesAdapter(getActivity(), garage);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RVGarage.setLayoutManager(layoutManager);
        RVGarage.setAdapter(adapter);
        return view;
    }

    public void readData() {
        String json_string = null;

        try {
            InputStream inputStream = getActivity().getAssets().open("garages.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json_string = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            garage = gson.fromJson(json_string, Garage.class);

            Log.d("DataIs", "readData: " + garage.getGarage().get(0).garageName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
