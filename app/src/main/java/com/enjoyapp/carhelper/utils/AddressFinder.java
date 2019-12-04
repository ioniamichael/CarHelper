package com.enjoyapp.carhelper.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.singletons.AddressSingleton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@SuppressLint("Registered")
public class AddressFinder extends AppCompatActivity {

    private LocationFinder finder;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private List<Address> addresses;
    private Context context;
    FragmentActivity activity;

    public AddressFinder(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void getLocation(TextView view) {
        double longitude = 0.0;
        double latitude = 0.0;
        finder = new LocationFinder(context);
        if (finder.canGetLocation()) {
            latitude = finder.getLatitude();
            longitude = finder.getLongitude();
        } else {
            finder.showSettingsAlert();
        }
        Locale lHebrew = new Locale("he");
        Geocoder gcd = new Geocoder(context, lHebrew);
        addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            view.setText(addresses.get(0).getLocality());
            AddressSingleton.getInstance().setmCurrentAddress(addresses.get(0).getLocality());
        } else {
            // do your stuff
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, // Changed from getActivity()
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, // Changed from getActivity()
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context
                        , R.style.AlertDialogCustom)
                        .setTitle("אישור למיקום שלך")
                        .setIcon(R.drawable.location_icon)
                        .setMessage("על מנת להציג לך את המוסכים הקרובים אלייך, אנו צריכים גישה למיקום שלך.")
                        .setPositiveButton("אוקיי!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity, // Changed from getActivity()
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity, // Changed from getActivity()
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(context, // Changed from getActivity()
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                }
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.

            }
            return;
        }
    }
}
