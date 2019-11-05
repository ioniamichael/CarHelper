package com.enjoyapp.carhelper.Models;

public class Light {

    private int lampType;
    private String lampTitle;
    private String lampDesc;
    private String lampImageUrl;

    public Light() {
    }

    public Light(int lampType, String lampTitle, String lampDesc, String lampImageUrl) {
        this.lampType = lampType;
        this.lampTitle = lampTitle;
        this.lampDesc = lampDesc;
        this.lampImageUrl = lampImageUrl;
    }

    public int getLampType() {
        return lampType;
    }

    public void setLampType(int lampType) {
        this.lampType = lampType;
    }

    public String getLampTitle() {
        return lampTitle;
    }

    public void setLampTitle(String lampTitle) {
        this.lampTitle = lampTitle;
    }

    public String getLampDesc() {
        return lampDesc;
    }

    public void setLampDesc(String lampDesc) {
        this.lampDesc = lampDesc;
    }

    public String getLampImageUrl() {
        return lampImageUrl;
    }

    public void setLampImageUrl(String lampImageUrl) {
        this.lampImageUrl = lampImageUrl;
    }
}
