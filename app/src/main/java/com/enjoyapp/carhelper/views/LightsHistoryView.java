package com.enjoyapp.carhelper.views;

import android.util.Log;

import androidx.annotation.NonNull;

import com.enjoyapp.carhelper.models.LightsHistory;
import com.enjoyapp.carhelper.presenters.LightsHistoryPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LightsHistoryView {

    private ArrayList<LightsHistory> lightsHistories = new ArrayList<>();
    private ArrayList<String> keys ;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private LightsHistory lightsHistory;
    private String TAG = "PushToDataBase";
    private LightsHistoryPresenter lightsHistoryPresenter;


    public LightsHistoryView() {
    }

    public LightsHistoryView(ArrayList<LightsHistory> lightsHistories, ArrayList<String> keys, LightsHistory lightsHistory, LightsHistoryPresenter lightsHistoryPresenter) {
        this.lightsHistories = lightsHistories;
        this.keys = keys;
        this.lightsHistory = lightsHistory;
        this.lightsHistoryPresenter = lightsHistoryPresenter;
    }

    public void writeItemToTB(Object object) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users")
                .child(mAuth.getCurrentUser().getUid())
                .child("UserLightsHistory");
        users.push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: true");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: false");
            }
        });
    }

    public void readItemsFromDB() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users")
                .child(mAuth.getCurrentUser().getUid())
                .child("UserLightsHistory");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keys = new ArrayList<>();
                lightsHistoryPresenter.clearRVBeforeLoadData();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    lightsHistory = dataSnapshot1.getValue(LightsHistory.class);
                    lightsHistories.add(0, lightsHistory);
                    keys.add(dataSnapshot1.getKey());
                    if (keys.size()>3){
                        users.child(keys.get(0)).removeValue();
                    }
                }
                lightsHistoryPresenter.setLightsHistoryAdapter();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
