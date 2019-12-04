package com.enjoyapp.carhelper.singletons;

public class UserSingleton {
    private static UserSingleton instance = null;
    private String mEmail;
    private String mName;


    private UserSingleton() {
        //Empty constructor
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public static void setInstance(UserSingleton instance) {
        UserSingleton.instance = instance;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public UserSingleton(String mEmail, String mName) {
        this.mEmail = mEmail;
        this.mName = mName;
    }
}
