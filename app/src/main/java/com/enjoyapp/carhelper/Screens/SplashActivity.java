package com.enjoyapp.carhelper.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Utils.Logs;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {

    private boolean dataReceived = false;
    private Logs logs = new Logs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
