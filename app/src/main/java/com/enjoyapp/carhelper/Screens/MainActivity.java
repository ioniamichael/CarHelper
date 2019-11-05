package com.enjoyapp.carhelper.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.enjoyapp.carhelper.Adapters.LightsAdapter;
import com.enjoyapp.carhelper.Fragments.LightsFragment;
import com.enjoyapp.carhelper.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLightsFragment();
    }

    public void showLightsFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new LightsFragment())
                .commit();
    }
}
