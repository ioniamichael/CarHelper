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
import com.enjoyapp.carhelper.Utils.AddressFinder;
import com.enjoyapp.carhelper.Utils.CustomToast;

public class GarageFragment extends Fragment {

    private TextView mTVAddress, mGaragesListCount;
    private AddressFinder mAddressFinder;
    private CustomToast mCustomToast;
    private GarageListFragment mGarageListFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage, container, false);
        mGarageListFragment = new GarageListFragment();
        mTVAddress = view.findViewById(R.id.TVAddress);
        mGaragesListCount = view.findViewById(R.id.garagesListCount);
        mAddressFinder = new AddressFinder(getContext(), getActivity());
        mCustomToast = new CustomToast(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAddress();
        showMap();
        showGaragesListFragment();
        getActivity().findViewById(R.id.BTNsort).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.BTNsort).setVisibility(View.VISIBLE);

    }

    public void getAddress() {
        if (mAddressFinder.checkLocationPermission()) {
            try {
                mAddressFinder.getLocation(mTVAddress);
            } catch (Exception e) {
                e.getMessage();
                mCustomToast.showToast(R.string.location_not_found);
            }
        }
    }

    public void showMap() {
        getFragmentManager().beginTransaction().replace(R.id.map_container, new MapsFragment())
                .commit();
    }

    public void showGaragesListFragment() {
        getFragmentManager().beginTransaction().replace(R.id.garageListContainer, mGarageListFragment)
                .commit();
    }
}