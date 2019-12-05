package com.enjoyapp.carhelper.presenters;

import android.app.Activity;

import com.enjoyapp.carhelper.models.Garage;

import java.util.ArrayList;

public interface GarageListPresenter {

    void setAdapter(Activity activity, ArrayList<Garage.GarageObject> garageObjects);

    void setGaragesCount(int garagesCount);
}
