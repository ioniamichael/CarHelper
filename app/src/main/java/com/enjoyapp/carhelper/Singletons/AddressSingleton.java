package com.enjoyapp.carhelper.Singletons;

public class AddressSingleton {

    private static AddressSingleton instance = null;
    private String mCurrentAddress = null;

    private AddressSingleton() {
    }

    public static AddressSingleton getInstance() {
        if (instance == null) {
            instance = new AddressSingleton();
        }
        return instance;
    }

    public String getmCurrentAddress() {
        return mCurrentAddress;
    }

    public void setmCurrentAddress(String mCurrentAddress) {
        this.mCurrentAddress = mCurrentAddress;
    }
}
