package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Screens.MainActivity;

public class SortFragment extends Fragment implements View.OnClickListener {

    private Button mFirst, mSecond, mThird;
    private Fragment currentFragment;
    private Fragment sortFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        initViews(view);
        setTextAndVisibility();
        return view;
    }

    //Initialize views.
    public void initViews(View view) {
        currentFragment = getFragmentManager().findFragmentById(R.id.main_fragment_container);
        sortFragment = getFragmentManager().findFragmentById(R.id.sort_fragment_container);
        mFirst = view.findViewById(R.id.firstButtonSort);
        mSecond = view.findViewById(R.id.secondButtonSort);
        mThird = view.findViewById(R.id.thirdButtonSort);
        mFirst.setOnClickListener(this);
        mSecond.setOnClickListener(this);
        mThird.setOnClickListener(this);
    }

    //Setting text according to main fragment - Lights fragment / Garage fragment.
    public void setTextAndVisibility() {
        if (currentFragment instanceof LightsFragment) {
            mFirst.setVisibility(View.VISIBLE);
            mSecond.setVisibility(View.VISIBLE);
            mFirst.setText("צבע הנורה");
            mSecond.setText("סוג תקלה");
        } else if (currentFragment instanceof GarageFragment) {
            mFirst.setVisibility(View.VISIBLE);
            mSecond.setVisibility(View.VISIBLE);
            mThird.setVisibility(View.VISIBLE);
            mFirst.setText("כתובת");
            mSecond.setText("בלה בלה");
            mThird.setText("סוג מוסך");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firstButtonSort:
                try {
                    if (currentFragment instanceof LightsFragment) {
                        ((LightsFragment) currentFragment).sortAdapter();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.removeSortFragment();
    }

}
