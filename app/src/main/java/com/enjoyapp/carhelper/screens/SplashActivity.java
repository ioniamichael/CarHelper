package com.enjoyapp.carhelper.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.enjoyapp.carhelper.presenters.SplashPresenter;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.views.SplashView;

public class SplashActivity extends AppCompatActivity implements SplashPresenter {

    private LottieAnimationView mSplashAnimation;
    private SplashView splashView;
    private TextView mLoadingText;
    private Animation zoomIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashView = new SplashView(this);
        mSplashAnimation = findViewById(R.id.splashAnimation);
        mLoadingText = findViewById(R.id.loadingText);
        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        mLoadingText.startAnimation(zoomIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSplashAnimation.playAnimation();
        splashView.loadData();
    }
    @Override
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
