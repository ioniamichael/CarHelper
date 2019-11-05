package com.enjoyapp.carhelper.Views;

import android.util.Log;

import androidx.annotation.NonNull;

import com.enjoyapp.carhelper.Models.Light;
import com.enjoyapp.carhelper.Presenters.LightsPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LightsView {

    private ArrayList<Light> lights = new ArrayList<>();
    private Light light;
    private FirebaseDatabase db;
    private DatabaseReference allLights;
    private LightsPresenter lightsPresenter;

    public LightsView(ArrayList<Light> lights, Light light, LightsPresenter lightsPresenter) {
        this.lights = lights;
        this.light = light;
        this.lightsPresenter = lightsPresenter;
    }

    public void loadData() {
        db = FirebaseDatabase.getInstance();
        allLights = db.getReference("AllLights");
        allLights.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    light = dataSnapshot1.getValue(Light.class);
                    lights.add(light);
                    Log.i("LightsDataLoaded", "true " + light.getLampTitle());
                }
                lightsPresenter.setAdapter();
                lightsPresenter.openLightsInfoFragment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("LightsDataLoaded", "false ");
            }
        });
    }

}
