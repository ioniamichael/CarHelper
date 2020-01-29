package com.enjoyapp.carhelper.views;

import android.content.Context;

import androidx.annotation.NonNull;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.presenters.EmailAndPasswordPresenter;
import com.enjoyapp.carhelper.presenters.SignInPresenter;
import com.enjoyapp.carhelper.singletons.UserSingleton;
import com.enjoyapp.carhelper.utils.CustomToast;
import com.enjoyapp.carhelper.utils.EmailAndPasswordValidation;
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
                        public void onSuccess(final AuthResult authResult) {
                            user.child(mAuth.getCurrentUser().getUid()).child("UserDetails")
                                    .setValue(UserSingleton.getInstance())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            signInPresenter.stopGetStartedButton();
                                            signInPresenter.showSuccessAnimation();
                                            //TODO change open splash activity to open main activity.
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    customToast.showToast(e.getMessage(), e);
                                    signInPresenter.stopGetStartedButton();
                                    signInPresenter.showFailedAnimation();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    customToast.showToast(e.getMessage(), e);
                    signInPresenter.stopGetStartedButton();
                    signInPresenter.showFailedAnimation();
                }
            });
        } else {
            customToast.showToast(R.string.wrong_email);
        }
    }

    public void signIn(String mEmail, String mPassword) {
        customToast = new CustomToast(context);
        validation = new EmailAndPasswordValidation();
        if (mEmail.isEmpty() || mPassword.isEmpty()) {
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
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    customToast.showToast(e.getMessage(), e);
                    signInPresenter.stopSignInButtonAnim();
                    signInPresenter.showFailedAnimation();
                }
            });
        } else {
            customToast.showToast(R.string.wrong_email);
        }
    }

    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public void signOut() {
        mAuth.signOut();
    }
}
