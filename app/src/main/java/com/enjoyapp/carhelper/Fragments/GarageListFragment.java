package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.Adapters.GaragesAdapter;
import com.enjoyapp.carhelper.Models.Garage;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Singletons.AddressSingleton;
import com.google.android.gms.common.util.Strings;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GarageListFragment extends Fragment {

    private RecyclerView RVGarage;
    private ArrayList<Garage> garages;
    private Garage garage;
    private ArrayList<Garage.GarageObject> garageObjects = new ArrayList<>();
    private GaragesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage_list, container, false);
        readData();
        RVGarage = view.findViewById(R.id.RVGarage);
        adapter = new GaragesAdapter(getActivity(), garageObjects);
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

            for (int i = 0; i < 13476; i++) {
                if (garage.getGarage().get(i).garageCity.equals(AddressSingleton.getInstance().getCurrentAddress())) {
                    garageObjects.add(garage.getGarage().get(i));
                    Log.d("TheDataIS", "readData: " + garage.getGarage().get(i).garageCity);
                }
            }

            Log.d("TheDataIS", "readDataresult: " + garageObjects.get(1).garageCity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}