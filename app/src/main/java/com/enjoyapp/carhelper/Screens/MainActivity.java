package com.enjoyapp.carhelper.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyapp.carhelper.Fragments.GarageFragment;
import com.enjoyapp.carhelper.Fragments.LightsFragment;
import com.enjoyapp.carhelper.Fragments.SortFragment;
import com.enjoyapp.carhelper.Models.Greetings;
import com.enjoyapp.carhelper.R;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Greetings mGreetings = new Greetings();
    private TextView mTVgreetings;
    private Button BTNmLights, BTNmGarage, mBTNsort;
    private LightsFragment lightsFragment = new LightsFragment();
    private GarageFragment garageFragment = new GarageFragment();
    private Fragment currentFragment;
    private Animation animation;
    private CardView mGarageBTNRootView, mLightsBTNRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFunctions();
    }

    public void initFunctions() {
        showLightsFragment();
        showGreetings();
    }

    public void showLightsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, this.lightsFragment)
                .commit();
    }

    public void showGarageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, this.garageFragment)
                .commit();
    }

    public void showGreetings() {
        mTVgreetings.setText(mGreetings.getGreetings());
    }

    public void initView() {
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        mTVgreetings = findViewById(R.id.TVgreetings);
        BTNmLights = findViewById(R.id.BTNlights);
        BTNmGarage = findViewById(R.id.BTNgarage);
        mBTNsort = findViewById(R.id.BTNsort);
        mLightsBTNRootView = findViewById(R.id.lightsBTNRootView);
        mGarageBTNRootView = findViewById(R.id.garageBTNRootView);
        BTNmLights.setOnTouchListener(this);
        BTNmGarage.setOnTouchListener(this);
        mBTNsort.setOnTouchListener(this);

    }

    public void openSortFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_left_to_right)
                .replace(R.id.sort_fragment_container, new SortFragment())
                .commit();
    }

    public void removeSortFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.sort_fragment_container);
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_left_to_right)
                    .remove(fragment)
                    .commit();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.BTNlights:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mLightsBTNRootView.startAnimation(animation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!(currentFragment instanceof LightsFragment)) {
                            showLightsFragment();
                        }
                        break;
                }
                break;
            case R.id.BTNgarage:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mGarageBTNRootView.startAnimation(animation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!(currentFragment instanceof GarageFragment)) {
                            showGarageFragment();
                        }
                        break;
                }
                break;
            case R.id.BTNsort:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mBTNsort.startAnimation(animation);
                        break;
                    case MotionEvent.ACTION_UP:
                        openSortFragment();
                        break;
                }
                break;
        }
        removeSortFragment();
        return true;
    }
}
