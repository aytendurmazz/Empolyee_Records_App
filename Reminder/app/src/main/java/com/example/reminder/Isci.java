package com.example.reminder;

import java.util.Date;
import java.util.UUID;

public class Isci {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private CharSequence mCinsiyet;
    private String adres;

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    private String mID;

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Isci() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public CharSequence getCinsiyet() {
        return mCinsiyet;
    }

    public void setmCinsiyet(CharSequence cinsiyet) {
        mCinsiyet = cinsiyet;
    }

}
