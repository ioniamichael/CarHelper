package com.enjoyapp.carhelper.Screens;

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

import com.enjoyapp.carhelper.Fragments.GarageFragment;
import com.enjoyapp.carhelper.Fragments.LightsFragment;
import com.enjoyapp.carhelper.Fragments.SortFragment;
import com.enjoyapp.carhelper.Models.Greetings;
import com.enjoyapp.carhelper.R;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Greetings mGreetings = new Greetings();
    private TextView mTVgreetings;
    private LightsFragment lightsFragment = new LightsFragment();
    private Button BTNmLights, BTNmGarage, mBTNsort;
    private GarageFragment garageFragment = new GarageFragment();
    private Fragment currentFragment;
    private Animation animation;
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
                .replace(R.id.main_fragment_container, new LightsFragment())
                .commit();
    }

    public void showGarageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, this.garageFragment)
                .commit();
    }

    public void showGreetings() {
        mTVgreetings.setText(mGreetings.getGreetings());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.BTNlights:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        removeSortFragment();
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
                        removeSortFragment();
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
                        if (!isSortFragmentOpen) {
                            openSortFragment();
                        } else removeSortFragment();
                        break;
                }
                break;
        }
        return true;

    }
}
