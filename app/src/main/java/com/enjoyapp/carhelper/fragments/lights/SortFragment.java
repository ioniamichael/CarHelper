package com.enjoyapp.carhelper.fragments.lights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.fragments.garage.GarageFragment;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.screens.MainActivity;

public class SortFragment extends Fragment implements View.OnClickListener {

    private Button mFirst, mSecond, mThird;
    private Fragment mCurrentFragment;
    private Fragment mSortFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        initViews(view);
        setTextAndVisibility();
        return view;
    }

    //Initialize views.
    private void initViews(View view) {
        mCurrentFragment = getFragmentManager().findFragmentById(R.id.main_fragment_container);
        mSortFragment = getFragmentManager().findFragmentById(R.id.sort_fragment_container);
        mFirst = view.findViewById(R.id.firstButtonSort);
        mSecond = view.findViewById(R.id.secondButtonSort);
        mThird = view.findViewById(R.id.thirdButtonSort);
        mFirst.setOnClickListener(this);
        mSecond.setOnClickListener(this);
        mThird.setOnClickListener(this);
    }

    //Setting text according to main fragment - Lights fragment / Garage fragment.
    private void setTextAndVisibility() {
        if (mCurrentFragment instanceof LightsFragment) {
            mFirst.setVisibility(View.VISIBLE);
            mSecond.setVisibility(View.VISIBLE);
            mFirst.setText("צבע הנורה");
//            mSecond.setText("סוג תקלה");
        } else if (mCurrentFragment instanceof GarageFragment) {
//            mFirst.setVisibility(View.VISIBLE);
//            mSecond.setVisibility(View.VISIBLE);
//            mThird.setVisibility(View.VISIBLE);
//            mFirst.setText("כתובת");
//            mSecond.setText("בלה בלה");
//            mThird.setText("סוג מוסך");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.firstButtonSort) {
            try {
                if (mCurrentFragment instanceof LightsFragment) {
                    ((LightsFragment) mCurrentFragment).sortAdapter();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.removeSortFragment();
    }

}
