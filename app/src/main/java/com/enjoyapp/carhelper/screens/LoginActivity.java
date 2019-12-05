package com.enjoyapp.carhelper.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.enjoyapp.carhelper.fragments.login.EmailAndPasswordFragment;
import com.enjoyapp.carhelper.fragments.successFailed.FailedFragment;
import com.enjoyapp.carhelper.fragments.successFailed.SuccessFragment;
import com.enjoyapp.carhelper.presenters.SignInPresenter;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.singletons.UserSingleton;
import com.enjoyapp.carhelper.views.SignInView;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, SignInPresenter {

    private Button mGetStarted, mSignIn;
    private EmailAndPasswordFragment mEmailAndPasswordFragment;
    private SuccessFragment successFragment;
    private FailedFragment failedFragment;
    private SignInView signInView;
    private String mName = null;
    private String mEmail = null;
    private String mPassword = null;
    private LottieAnimationView mLAVLogin, mLAVGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initClasses();
        initView();
        showEmailAndPasswordFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (signInView.isLoggedIn()) {
            openSplashActivity();
        }
    }

    public void initClasses() {
        mEmailAndPasswordFragment = new EmailAndPasswordFragment();
        successFragment = new SuccessFragment();
        failedFragment = new FailedFragment();
        signInView = new SignInView(this, mEmailAndPasswordFragment, getApplicationContext());
    }

    public void initView() {
        mGetStarted = findViewById(R.id.getStartedBTN);
        mSignIn = findViewById(R.id.logInBTN);
        mGetStarted.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
        mLAVLogin = findViewById(R.id.LAVLogin);
        mLAVGetStarted = findViewById(R.id.LAVGetStarted);
    }

    //Open splash activity after Success animation end.
    public void openSplashActivity() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    //Add fragment that contain Email and Password fields on Login screen.
    public void showEmailAndPasswordFields() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                .replace(R.id.sign_in_fields_container, this.mEmailAndPasswordFragment)
                .commit();
    }

    //Getting name, email and password from email and password fragment and setting them in UserSingleton.
    public void setUserDetailsFromInputFields() {
        mName = ((EditText) mEmailAndPasswordFragment.getView()
                .findViewById(R.id.ETName))
                .getText().toString().trim();
        mEmail = ((EditText) mEmailAndPasswordFragment.getView()
                .findViewById(R.id.ETEmail))
                .getText().toString().trim();
        mPassword = ((EditText) mEmailAndPasswordFragment.getView()
                .findViewById(R.id.ETPass))
                .getText().toString().trim();

        UserSingleton.getInstance().setmName(mName);
        UserSingleton.getInstance().setmEmail(mEmail);
    }

    //On click listener for sign in and get started button on main screen.
    @Override
    public void onClick(View view) {
        setUserDetailsFromInputFields();
        switch (view.getId()) {
            case R.id.getStartedBTN:
                signInView.registerUser(mName, mEmail, mPassword); // User registration
                //TODO Registration
                break;
            case R.id.logInBTN:
                signInView.signIn(mName, mEmail, mPassword); // User sign in
                //TODO Sign in
                break;
        }
    }

    //Adding success animation as fragment by replace Email and Password fragment
    @Override
    public void showSuccessAnimation() {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.sign_in_fields_container, this.successFragment)
                .commit();
    }

    //Adding failed animation as fragment by replace Email and Password fragment
    //After2 seconds , fragment removed automatically and email and password fragment appear again.
    @Override
    public void showFailedAnimation() {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.sign_in_fields_container, new FailedFragment())
                .commit();
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                showEmailAndPasswordFields();
            }
        }, 1900);

    }


    //Stopping button animation according top animated button.
    @Override
    public void stopGetStartedButton() {
        mLAVGetStarted.pauseAnimation();
        mLAVGetStarted.setVisibility(View.GONE);
        mGetStarted.setVisibility(View.VISIBLE);
    }

    //Animation for sign in button.
    @Override
    public void stopSignInButtonAnim() {
        mLAVLogin.pauseAnimation();
        mLAVLogin.setVisibility(View.GONE);
        mSignIn.setVisibility(View.VISIBLE);
    }

    //Starting animation on get started button
    @Override
    public void showGetStartedBTNAnimation() {
        mGetStarted.setVisibility(View.GONE);
        mLAVGetStarted.setVisibility(View.VISIBLE);
    }

    //Starting animation on sign in button
    @Override
    public void showSignInBTNAnimation() {
        mSignIn.setVisibility(View.GONE);
        mLAVLogin.setVisibility(View.VISIBLE);
    }


}
