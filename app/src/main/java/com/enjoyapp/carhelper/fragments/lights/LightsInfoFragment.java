package com.enjoyapp.carhelper.fragments.lights;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.enjoyapp.carhelper.R;

import java.util.Objects;


public class LightsInfoFragment extends Fragment implements View.OnClickListener {

    private TextView mLightTitle, mLightDesc, mLightType;
    private String mLightsTitle, mLightsDesc, mImageURL;
    private ImageView mLightIMG;
    private ImageView mBackButton, mWhatsAppButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mLightsTitle = getArguments().getString("LIGHTS_TITLE");
        mLightsDesc = getArguments().getString("LIGHTS_DESC");
        mImageURL = getArguments().getString("IMAGE_URL");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights_info, container, false);
        mLightTitle = view.findViewById(R.id.lightTitle);
        mLightDesc = view.findViewById(R.id.lightDesc);
        mLightType = view.findViewById(R.id.lightType);
        mLightIMG = view.findViewById(R.id.lightIMG);
        mBackButton = view.findViewById(R.id.backButton);
        mWhatsAppButton = view.findViewById(R.id.whatsAppButton);
        mWhatsAppButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        showFragment();
        return view;
    }

    private void showFragment() {
        mLightTitle.setText(mLightsTitle);
        mLightDesc.setText(mLightsDesc);
        Glide.with(this)
                .load(mImageURL)
                .override(200, 200)
                .into(mLightIMG);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.whatsAppButton:
                sendMessage();
                break;
        }
    }

    //Send message
    private void sendMessage() {
        Intent sendMessage = new Intent(android.content.Intent.ACTION_SEND);
        sendMessage.setType("text/plain");
        sendMessage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        sendMessage.putExtra(Intent.EXTRA_SUBJECT, "נדלקה לי מנורה ברכב");
        sendMessage.putExtra(Intent.EXTRA_TEXT, mLightsTitle + " : \n " + mLightsDesc);

        startActivity(Intent.createChooser(sendMessage, "שתפו את התקלה עם חברים"));
    }
}
