package com.enjoyapp.carhelper.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.models.Garage;
import com.enjoyapp.carhelper.presenters.GarageListPresenter;
import com.enjoyapp.carhelper.singletons.AddressSingleton;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GarageListView {

    private String TAG = "ReadingGSON";

    private Garage mGarage;
    private ArrayList<Garage.GarageObject> mGarageObjects = new ArrayList<>();
    private GarageListPresenter presenter;
    private Context mContext;
    private Activity activity;

    public GarageListView(GarageListPresenter presenter, Context mContext,Activity activity) {
        this.presenter = presenter;
        this.mContext = mContext;
        this.activity = activity;
    }

    public void loadData(){
        Log.d(TAG, "loadData: starting loadData()");
        String json_string;

        try {
            Log.d(TAG, "loadData: ");
            InputStream inputStream = activity.getAssets().open(mContext.getString(R.string.garage_json));
            Log.d(TAG, "loadData: " +inputStream);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json_string = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            mGarage = gson.fromJson(json_string, Garage.class);


            for (int i = 0; i < 13273; i++) {
                if (mGarage.getGarage().get(i).garageCity.equals(AddressSingleton.getInstance().getmCurrentAddress())) {

                    if (!mGarageObjects.contains(mGarage.getGarage().get(i))) {
                        mGarageObjects.add(mGarage.getGarage().get(i));
                    }
                }
            }
            Log.d(TAG, "loadData: " +mGarageObjects.size());
            presenter.setAdapter(activity, mGarageObjects);

            presenter.setGaragesCount(mGarageObjects.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
