package com.enjoyapp.carhelper.Views;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.enjoyapp.carhelper.Presenters.EmailAndPasswordPresenter;
import com.enjoyapp.carhelper.Presenters.SignInPresenter;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Singletons.UserSingleton;
import com.enjoyapp.carhelper.Utils.CustomToast;
import com.enjoyapp.carhelper.Utils.EmailAndPasswordValidation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInView {

    String TAG = "UserRegistration";

    private EmailAndPasswordValidation validation;


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference user = db.getReference("Users");

    private SignInPresenter signInPresenter;
    private EmailAndPasswordPresenter emailAndPasswordPresenter;
    private Context context;

    private CustomToast customToast;

    public SignInView(SignInPresenter signInPresenter, EmailAndPasswordPresenter emailAndPasswordPresenter, Context context) {
        this.signInPresenter = signInPresenter;
        this.emailAndPasswordPresenter = emailAndPasswordPresenter;
        this.context = context;
    }

    public SignInView() {
    }

    public void registerUser(String mName, String mEmail, String mPassword) {
        customToast = new CustomToast(context);
        validation = new EmailAndPasswordValidation();
        if (mEmail.isEmpty() || mName.isEmpty() || mPassword.isEmpty()) {
            emailAndPasswordPresenter.setUiForWrongInputs();
            return;
        }
        if (validation.isValidEmail(mEmail)) {
            signInPresenter.showGetStartedBTNAnimation();
            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            user.child(mAuth.getCurrentUser().getUid()).child("UserDetails")
                                    .setValue(UserSingleton.getInstance())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            signInPresenter.stopGetStartedButton();
                                            signInPresenter.showSuccessAnimation();
                                            //TODO change open splash activity to open main activity.
                                            Log.d(TAG, "onSuccess: DataBase writed " + UserSingleton.getInstance());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: User not writed to database " + e.getMessage());
                                    customToast.showToast(e.getMessage());
                                    signInPresenter.stopGetStartedButton();
                                    signInPresenter.showFailedAnimation();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    customToast.showToast(e.getMessage());
                    signInPresenter.stopGetStartedButton();
                    signInPresenter.showFailedAnimation();
                }
            });
        } else {
            customToast.showToast(R.string.wrong_email);
        }
    }

    public void signIn(String mName, String mEmail, String mPassword) {
        customToast = new CustomToast(context);
        validation = new EmailAndPasswordValidation();
        if (mEmail.isEmpty() || mName.isEmpty() || mPassword.isEmpty()) {
            emailAndPasswordPresenter.setUiForWrongInputs();
            return;
        }
        if (validation.isValidEmail(mEmail)) {
            signInPresenter.showSignInBTNAnimation();
            mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            signInPresenter.stopSignInButtonAnim();
                            signInPresenter.showSuccessAnimation();
                            Log.d(TAG, "Sign in success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    customToast.showToast(e.getMessage());
                    Log.d(TAG, "onFailure: SignIn failure");
                    signInPresenter.stopSignInButtonAnim();
                    signInPresenter.showFailedAnimation();
                }
            });
        } else {
            Log.d(TAG, "signIn: Wrong Email");
            customToast.showToast(R.string.wrong_email);
        }
    }

    public boolean isLoggedIn() {
        if (mAuth.getCurrentUser() != null) {
            return true;
        }
        return false;
    }

    public void signOut() {
        mAuth.signOut();
    }
}
