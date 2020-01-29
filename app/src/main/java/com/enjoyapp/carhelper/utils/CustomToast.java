package com.enjoyapp.carhelper.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.screens.LoginActivity;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class CustomToast {

    private Context context;
    private String ERROR_TEXT;

    public CustomToast(Context context) {
        this.context = context;
    }

    public CustomToast() {
    }

    public void showToast(String toastText, Exception e) {

        if (e instanceof FirebaseAuthInvalidUserException) {
            ERROR_TEXT = "משתמש לא נמצא , אנא הירשם.";
        }
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            ERROR_TEXT = "סיסמה שגויה , אנא נסה שנית.";
        }
        if (e instanceof FirebaseNetworkException) {
            ERROR_TEXT = "ישנה בעיית תקשורת , אנא בדוק את החיבור לרשת שלך.";

        }

        Toast toast = Toast.makeText(context, ERROR_TEXT, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setPadding(20, 10, 20, 10);
        view.setMinimumHeight(250);
        view.setBackgroundResource(R.drawable.light_info_frame_style);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void showToast(int wrong_email) {
        Toast toast = Toast.makeText(context, wrong_email, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setPadding(50, 10, 50, 10);
        view.setMinimumHeight(250);
        view.setBackgroundResource(R.drawable.light_info_frame_style);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        toast.show();
    }
}
