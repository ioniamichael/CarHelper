package com.enjoyapp.carhelper.fragments.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoyapp.carhelper.presenters.EmailAndPasswordPresenter;
import com.enjoyapp.carhelper.R;

import java.util.ArrayList;

public class EmailAndPasswordFragment extends Fragment implements EmailAndPasswordPresenter {

    private EditText mETemail, mETpassword, mETName;
    private ArrayList<EditText> UIComponents;
    private TextView mRequiredFieldsTXT;
    private Animation animation;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_email_and_password, container, false);
        return view;
    }

    @Override
    public void onResume() {
        initView(view);
        super.onResume();
    }

    public void initView(View view) {
        mETName = view.findViewById(R.id.ETName);
        mETemail = view.findViewById(R.id.ETEmail);
        mETpassword = view.findViewById(R.id.ETPass);
        mRequiredFieldsTXT = getActivity().findViewById(R.id.requiredFieldsTXT);
        mRequiredFieldsTXT.setVisibility(View.GONE);
    }

    public ArrayList<EditText> getUIComponents() {
        UIComponents = new ArrayList();
        UIComponents.add(mETemail);
        UIComponents.add(mETName);
        UIComponents.add(mETpassword);
        return UIComponents;
    }

    @Override
    public void setUiForWrongInputs() {
        for (final EditText component : getUIComponents()) {
            if (component.getText().toString().trim().isEmpty()) {
                component.setBackgroundResource(R.drawable.wrong_input);
                mRequiredFieldsTXT.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                mRequiredFieldsTXT.startAnimation(animation);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        component.setBackgroundResource(R.drawable.light_info_frame_style);
                        animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                        mRequiredFieldsTXT.startAnimation(animation);
                        mRequiredFieldsTXT.setVisibility(View.GONE);

                        UIComponents = null;
                    }
                }, 2000);
            }
        }
    }
}