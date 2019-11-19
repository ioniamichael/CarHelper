package com.enjoyapp.carhelper.Singletons;

public class AddressSingleton {

    private static AddressSingleton instance = null;
    private String currentAddress = null;

    private AddressSingleton() {
    }

    public static AddressSingleton getInstance() {
        if (instance == null) {
            instance = new AddressSingleton();
        }
        return instance;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
}
