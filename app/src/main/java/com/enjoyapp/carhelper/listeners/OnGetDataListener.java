package com.enjoyapp.carhelper.listeners;

import com.google.firebase.database.DataSnapshot;

public interface OnGetDataListener {

    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();

}
