package com.enjoyapp.carhelper.views;

import android.util.Log;

import androidx.annotation.NonNull;

import com.enjoyapp.carhelper.listeners.OnGetDataListener;
import com.enjoyapp.carhelper.models.Light;
import com.enjoyapp.carhelper.presenters.LightsPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LightsView {

    private ArrayList<Light> mLightsArray;
    private Light mLight;
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference mLightsRef = mDB.getReference("AllLights");
    private LightsPresenter mLightsPresenter;

    public LightsView(ArrayList<Light> mLightsArray, Light mLight, LightsPresenter mLightsPresenter) {
        this.mLightsArray = mLightsArray;
        this.mLight = mLight;
        this.mLightsPresenter = mLightsPresenter;
    }

    public void loadData(){
        readData(mLightsRef, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d("DataLoaded", "onSuccess: ");
                mLightsPresenter.stopLoadAnimation();
                mLightsPresenter.setAdapter();
            }

            @Override
            public void onStart() {
                Log.d("DataLoaded", "onStart: ");
                mLightsPresenter.clearRecyclerView();
                mLightsPresenter.startLoadAnimation();

            }

            @Override
            public void onFailure() {
                Log.d("DataLoaded", "onFailure: ");
            }
        });
    }


    //OnGetDataListener Interface declaration
    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    mLight = dataSnapshot1.getValue(Light.class);
                    mLightsArray.add(mLight);

                }
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();

            }
        });

    }


}
