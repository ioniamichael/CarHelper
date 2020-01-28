package com.enjoyapp.carhelper.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.fragments.garage.GarageFragment;
import com.enjoyapp.carhelper.fragments.lights.LightsFragment;
import com.enjoyapp.carhelper.fragments.lights.SortFragment;
import com.enjoyapp.carhelper.models.Greetings;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.singletons.UserSingleton;
import com.enjoyapp.carhelper.utils.CustomToast;
import com.enjoyapp.carhelper.views.SignInView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Greetings mGreetings = new Greetings();
    private TextView mTVgreetings;
    private LightsFragment mLightsFragment = new LightsFragment();
    private Button mBTNmLights, mBTNmGarage, mBTNsort;
    private ImageView mDisconnectBTN;
    private GarageFragment mGarageFragment = new GarageFragment();
    private Fragment mCurrentFragment;
    private Animation mAnimation;
    private boolean isSortFragmentOpen = false;
    private CardView mGarageBTNRootView, mLightsBTNRootView;
    private SignInView signInView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInView = new SignInView();
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
        mTVgreetings = findViewById(R.id.TVGreetings);
        mDisconnectBTN = findViewById(R.id.disconnectBTN);
        mBTNmLights = findViewById(R.id.BTNLights);
        mBTNmGarage = findViewById(R.id.BTNGarage);
        mBTNsort = findViewById(R.id.BTNSort);
        mLightsBTNRootView = findViewById(R.id.lightsBTNRootView);
        mGarageBTNRootView = findViewById(R.id.garageBTNRootView);
        mBTNmLights.setOnTouchListener(this);
        mBTNmGarage.setOnTouchListener(this);
        mBTNsort.setOnTouchListener(this);
        mDisconnectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInView.signOut();
                openLoginActivity();
            }
        });

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
            case R.id.BTNLights:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        removeSortFragment();
                        mAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mLightsBTNRootView.startAnimation(mAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!(mCurrentFragment instanceof LightsFragment)) {
                            showLightsFragment();
                            mBTNsort.setAlpha(1);
                            mBTNsort.setEnabled(true);
                        }
                        break;
                }
                break;
            case R.id.BTNGarage:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        removeSortFragment();
                        mAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
                        mGarageBTNRootView.startAnimation(mAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!(mCurrentFragment instanceof GarageFragment)) {
                            showGarageFragment();
                            mBTNsort.animate().alpha(0);
                            mBTNsort.setEnabled(false);
                        }
                        break;
                }
                break;
            case R.id.BTNSort:
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.lightsInfoContainer);
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
