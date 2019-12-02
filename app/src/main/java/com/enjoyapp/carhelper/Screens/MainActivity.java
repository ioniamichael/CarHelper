package com.enjoyapp.carhelper.Screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.Fragments.Garage.GarageFragment;
import com.enjoyapp.carhelper.Fragments.Lights.LightsFragment;
import com.enjoyapp.carhelper.Fragments.Lights.SortFragment;
import com.enjoyapp.carhelper.Models.Greetings;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Singletons.UserSingleton;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Greetings mGreetings = new Greetings();
    private TextView mTVgreetings;
    private LightsFragment mLightsFragment = new LightsFragment();
    private Button mBTNmLights, mBTNmGarage, mBTNsort;
    private GarageFragment mGarageFragment = new GarageFragment();
    private Fragment mCurrentFragment;
    private Animation mAnimation;
    private boolean isSortFragmentOpen = false;
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

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        mTVgreetings = findViewById(R.id.TVgreetings);
        mBTNmLights = findViewById(R.id.BTNlights);
        mBTNmGarage = findViewById(R.id.BTNgarage);
        mBTNsort = findViewById(R.id.BTNsort);
        mLightsBTNRootView = findViewById(R.id.lightsBTNRootView);
        mGarageBTNRootView = findViewById(R.id.garageBTNRootView);
        mBTNmLights.setOnTouchListener(this);
        mBTNmGarage.setOnTouchListener(this);
        mBTNsort.setOnTouchListener(this);

    }

    public void openSortFragment() {
        isSortFragmentOpen = true;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_left_to_right)
                .replace(R.id.sort_fragment_container, new SortFragment())
                .commit();
    }

    public void removeSortFragment() {
        isSortFragmentOpen = false;
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.sort_fragment_container);
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_left_to_right)
                    .remove(fragment)
                    .commit();
        }
    }

    public void showLightsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, this.mLightsFragment)
                .commit();
    }

    public void showGarageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, this.mGarageFragment)
                .commit();
    }

    public void showGreetings() {
        mTVgreetings.setText(mGreetings.getGreetings() + ", " + UserSingleton.getInstance().getmName() + "!");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.BTNlights:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        removeSortFragment();
                        mAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mLightsBTNRootView.startAnimation(mAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!(mCurrentFragment instanceof LightsFragment)) {
                            showLightsFragment();
                        }
                        break;
                }
                break;
            case R.id.BTNgarage:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        removeSortFragment();
                        mAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mGarageBTNRootView.startAnimation(mAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!(mCurrentFragment instanceof GarageFragment)) {
                            showGarageFragment();
                        }
                        break;
                }
                break;
            case R.id.BTNsort:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mBTNsort.startAnimation(mAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isSortFragmentOpen) {
                            openSortFragment();
                        } else removeSortFragment();
                        break;
                }
                break;
        }
        removeLightsInfoFragment();
        return true;
    }

    private void removeLightsInfoFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.light_info_container);
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }
}
