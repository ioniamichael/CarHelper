package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Utils.AddressFinder;
import com.enjoyapp.carhelper.Utils.CustomToast;

public class GarageFragment extends Fragment {

    private TextView mTVAddress;
    private AddressFinder addressFinder;
    private CustomToast customToast;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage, container, false);
        mTVAddress = view.findViewById(R.id.TVAddress);
        addressFinder = new AddressFinder(getContext(), getActivity());
        customToast = new CustomToast(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAddress();
        showMap();
        showGaragesListFragment();

    }

    public void getAddress() {
        if (addressFinder.checkLocationPermission()) {
            try {
                addressFinder.getLocation(mTVAddress);
            } catch (Exception e) {
                e.getMessage();
                customToast.showToast(R.string.location_not_found);
            }
        }
    }

    public void showMap() {
        getFragmentManager().beginTransaction().replace(R.id.map_container, new MapsFragment())
                .commit();
    }

    public void showGaragesListFragment() {
        getFragmentManager().beginTransaction().replace(R.id.garageListContainer, new GarageListFragment())
                .commit();
    }

}
