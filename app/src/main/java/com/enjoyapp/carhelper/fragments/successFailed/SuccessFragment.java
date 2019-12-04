package com.enjoyapp.carhelper.fragments.successFailed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.screens.LoginActivity;
import com.enjoyapp.carhelper.screens.SplashActivity;

public class SuccessFragment extends Fragment {

    private LottieAnimationView mLAVSuccessAnimation, mLAVConfetti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        mLAVSuccessAnimation = view.findViewById(R.id.LAVSuccessAnimation);
        mLAVConfetti = view.findViewById(R.id.LAVConfetti);
        mLAVSuccessAnimation.playAnimation();
        mLAVConfetti.playAnimation();
        mLAVSuccessAnimation.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (getActivity() instanceof LoginActivity) {
                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
        return view;
    }
}
