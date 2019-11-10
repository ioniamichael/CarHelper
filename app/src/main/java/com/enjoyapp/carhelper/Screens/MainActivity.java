package com.enjoyapp.carhelper.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.enjoyapp.carhelper.Fragments.GarageFragment;
import com.enjoyapp.carhelper.Fragments.LightsFragment;
import com.enjoyapp.carhelper.Fragments.SortFragment;
import com.enjoyapp.carhelper.Models.Greetings;
import com.enjoyapp.carhelper.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Greetings mGreetings = new Greetings();
    private TextView mTVgreetings;
    private Button BTNmLights, BTNmGarage, mBTNsort;
    private LightsFragment lightsFragment = new LightsFragment();
    private GarageFragment garageFragment = new GarageFragment();
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFunctions();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTNlights:
                if (!(currentFragment instanceof LightsFragment)) {
                    showLightsFragment();
                }
                break;
            case R.id.BTNgarage:
                if (!(currentFragment instanceof GarageFragment)) {
                    showGarageFragment();
                }
                showGarageFragment();
                break;
            case R.id.BTNsort:
                openSortFragment();
                break;
        }
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
        BTNmLights.setOnClickListener(this);
        BTNmGarage.setOnClickListener(this);
        mBTNsort.setOnClickListener(this);
    }

    public void openSortFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_left_to_right)
                .replace(R.id.sort_fragment_container, new SortFragment())
                .commit();
    }

}
