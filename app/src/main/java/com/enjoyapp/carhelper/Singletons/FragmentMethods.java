package com.enjoyapp.carhelper.Singletons;

public class FragmentMethods {

    private boolean isInfoFragmentOpened = false;

    public boolean isInfoFragmentOpened() {
        return isInfoFragmentOpened;
    }

    public void setInfoFragmentOpened(boolean infoFragmentOpened) {
        isInfoFragmentOpened = infoFragmentOpened;
    }

    private static FragmentMethods instance = null;

    private FragmentMethods() {
    }

    public static FragmentMethods getInstance() {
        if (instance == null) {
            instance = new FragmentMethods();
        }
        return instance;
    }

}
