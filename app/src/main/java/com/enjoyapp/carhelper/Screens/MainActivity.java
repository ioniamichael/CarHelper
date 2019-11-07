package com.enjoyapp.carhelper.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.enjoyapp.carhelper.Adapters.LightsAdapter;
import com.enjoyapp.carhelper.Fragments.LightsFragment;
import com.enjoyapp.carhelper.Models.Greetings;
import com.enjoyapp.carhelper.Models.Light;
import com.enjoyapp.carhelper.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Greetings mGreetings = new Greetings();
    private TextView mTVgreetings;
    private Button BTNmLights, BTNmGarage;
    private LightsFragment lightsFragment = new LightsFragment();

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
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, this.lightsFragment)
                .commit();
    }

    public void showGreetings() {
        mTVgreetings.setText(mGreetings.getGreetings());
    }

    public void initView() {
        mTVgreetings = findViewById(R.id.TVgreetings);
        BTNmLights = findViewById(R.id.BTNlights);
        BTNmGarage = findViewById(R.id.BTNgarage);
        BTNmLights.setOnClickListener(this);
        BTNmGarage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTNlights:
                //TODO open lights fragment...
                lightsFragment.sortAdapter();
                break;
            case R.id.BTNgarage:
                //TODO open garage fragment...
                break;
        }
    }
}
