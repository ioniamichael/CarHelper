package com.enjoyapp.carhelper.views;

import android.util.Log;

import androidx.annotation.NonNull;

import com.enjoyapp.carhelper.listeners.OnGetDataListener;
import com.enjoyapp.carhelper.presenters.SplashPresenter;
import com.enjoyapp.carhelper.singletons.UserSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashView {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference user = db.getReference("Users");

    private SplashPresenter presenter;
    private String mName;

    public SplashView(SplashPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadData() {
        getUserName(user, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                UserSingleton.getInstance().setmName(mName);
                presenter.openMainActivity();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void getUserName(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mName = dataSnapshot.child(mAuth.getCurrentUser()
                        .getUid()).child("UserDetails")
                        .child("mName")
                        .getValue(String.class);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();

            }
        });
    }
}
