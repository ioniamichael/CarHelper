package com.enjoyapp.carhelper.fragments.garage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.utils.AddressFinder;
import com.enjoyapp.carhelper.utils.CustomToast;

import org.w3c.dom.Text;

import java.util.Objects;

public class GarageFragment extends Fragment {

    private TextView mTVAddress, mGaragesListCount;
    private AddressFinder mAddressFinder;
    private CustomToast mCustomToast;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage, container, false);
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
        showGarageListFragment();
        getActivity().findViewById(R.id.BTNSort).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.BTNSort).setVisibility(View.VISIBLE);

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
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.map_container, new MapsFragment())
                    .commit();
        }
    }
    private void showGarageListFragment(){
        getFragmentManager().beginTransaction().replace(R.id.garageListContainer, new GarageListFragment()).commit();
    }
}