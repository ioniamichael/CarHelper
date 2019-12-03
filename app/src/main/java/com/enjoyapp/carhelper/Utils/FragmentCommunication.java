package com.enjoyapp.carhelper.Utils;

public interface FragmentCommunication {
    void respond(int position,String lightsTitle,String lightsDesc, String imageURL);
    void respond(int position, String phoneNumber);
}
