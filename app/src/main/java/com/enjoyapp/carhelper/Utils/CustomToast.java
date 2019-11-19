package com.enjoyapp.carhelper.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyapp.carhelper.R;

public class CustomToast {

    private Context context;

    public CustomToast(Context context) {
        this.context = context;
    }

    public void showToast(int toastText){
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setPadding(50, 10, 50, 10);
        view.setBackgroundResource(R.drawable.light_info_frame_style);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.BLACK);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toast.show();
    }
}
