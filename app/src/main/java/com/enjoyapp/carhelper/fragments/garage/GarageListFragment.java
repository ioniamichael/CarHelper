package com.enjoyapp.carhelper.fragments.garage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.adapters.GaragesAdapter;
import com.enjoyapp.carhelper.models.Garage;
import com.enjoyapp.carhelper.presenters.GarageListPresenter;
import com.enjoyapp.carhelper.views.GarageListView;

import java.util.ArrayList;

public class GarageListFragment extends Fragment implements GarageListPresenter {

    private RecyclerView mRVGarage;
    private Fragment fragment;
    private Garage mGarage;
    private ArrayList<Garage.GarageObject> mGarageObjects = new ArrayList<>();
    private GaragesAdapter mAdapter;
    private TextView mGarageCount;
    private GarageListView garageListView;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage_list, container, false);
        mRVGarage = view.findViewById(R.id.RVGarage);
        garageListView = new GarageListView(this, getContext(), getActivity());
        garageListView.loadData();

        return view;
    }

    private void dialGarage(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    //Set garages count on garage fragment
    @Override
    public void setGaragesCount(int garagesCount) {
        fragment = getFragmentManager().findFragmentById(R.id.main_fragment_container);
        if (fragment != null) {
            mGarageCount = fragment.getView().findViewById(R.id.garagesListCount);
        }
        mGarageCount.setText(String.valueOf(mGarageObjects.size()));
        mGarageCount.setText("נמצאו " + garagesCount + " מוסכים בסביבתך! ");
    }

    @Override
    public void setAdapter(Activity activity, ArrayList<Garage.GarageObject> garageObjects) {
        mAdapter = new GaragesAdapter(activity, garageObjects);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRVGarage.setLayoutManager(layoutManager);
        mRVGarage.setAdapter(mAdapter);
        mAdapter.setOnPhoneNumberClickListener(new GaragesAdapter.OnPhoneNumberClickListener() {
            @Override
            public void onPhoneNumberClick(int position, String phone) {
                dialGarage(phone);
            }

            @Override
            public void onRootViewClick(int position) {

            }
        });
    }
}