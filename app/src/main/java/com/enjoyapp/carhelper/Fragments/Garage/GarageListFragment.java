package com.enjoyapp.carhelper.Fragments.Garage;

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
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class GarageListFragment extends Fragment {

    private RecyclerView mRVGarage;
    private Garage mGarage;
    private ArrayList<Garage.GarageObject> mGarageObjects = new ArrayList<>();
    private GaragesAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage_list, container, false);
        readData();
        mRVGarage = view.findViewById(R.id.RVGarage);
        mAdapter = new GaragesAdapter(getActivity(), mGarageObjects);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRVGarage.setLayoutManager(layoutManager);
        mRVGarage.setAdapter(mAdapter);

        return view;
    }

    public void readData() {
        String json_string = null;

        try {
            InputStream inputStream = Objects.requireNonNull(getActivity()).getAssets().open(getString(R.string.garage_json));
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json_string = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            mGarage = gson.fromJson(json_string, Garage.class);


            for (int i = 0; i < 13273; i++) {
                if (mGarage.getGarage().get(i).garageCity.equals(AddressSingleton.getInstance().getmCurrentAddress())) {
                    if (!mGarageObjects.contains(mGarage.getGarage().get(i))) {
                        mGarageObjects.add(mGarage.getGarage().get(i));
//                        Log.d("AfterEdit", "readData: " + mGarageObjects.get(i).garageName);
                    }
                }
            }

            Log.d("AfterEdit", "readData: " + mGarageObjects.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}