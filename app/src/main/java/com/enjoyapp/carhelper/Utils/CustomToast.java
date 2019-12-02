package com.enjoyapp.carhelper.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyapp.carhelper.R;

public class CustomToast {

    private Context context;

    public CustomToast(Context context) {
        this.context = context;
    }

    public CustomToast() {
    }

    public void showToast(String toastText){
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setPadding(20, 10, 20, 10);
        view.setMinimumHeight(250);
        view.setBackgroundResource(R.drawable.light_info_frame_style);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void showToast(int wrong_email){
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
