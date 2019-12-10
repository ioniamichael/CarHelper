package com.enjoyapp.carhelper.models;

public class LightsHistory {

    private String mLightsIMG;
    private String mLightsTitle;
    private String mLightsDesc;
    private String mAddedDate;

    public LightsHistory() {
    }

    public String getmLightsIMG() {
        return mLightsIMG;
    }

    public void setmLightsIMG(String mLightsIMG) {
        this.mLightsIMG = mLightsIMG;
    }

    public String getmLightsTitle() {
        return mLightsTitle;
    }

    public void setmLightsTitle(String mLightsTitle) {
        this.mLightsTitle = mLightsTitle;
    }

    public String getmLightsDesc() {
        return mLightsDesc;
    }

    public void setmLightsDesc(String mLightsDesc) {
        this.mLightsDesc = mLightsDesc;
    }

    public String getmAddedDate() {
        return mAddedDate;
    }

    public void setmAddedDate(String mAddedDate) {
        this.mAddedDate = mAddedDate;
    }

    public LightsHistory(String mLightsIMG, String mLightsTitle, String mLightsDesc, String mAddedDate) {
        this.mLightsIMG = mLightsIMG;
        this.mLightsTitle = mLightsTitle;
        this.mLightsDesc = mLightsDesc;
        this.mAddedDate = mAddedDate;
    }
}
