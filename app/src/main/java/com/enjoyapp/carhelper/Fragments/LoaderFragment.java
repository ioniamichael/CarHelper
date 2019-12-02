package com.enjoyapp.carhelper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.enjoyapp.carhelper.R;

public class LoaderFragment extends Fragment {
    private View view;
    private LottieAnimationView mLAVCar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loader, container, false);
        mLAVCar = view.findViewById(R.id.LAVcar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLAVCar.playAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mLAVCar = null;
    }

}
