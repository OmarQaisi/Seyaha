package com.example.seyaha;

public class Hotel {

    private String mThumbnail;
    private String mTitle;
    private String mHours;
    private String mRating;
    private String mPlaceId;
    private double mLat;
    private double mLng;

    public Hotel() {
    }

    public Hotel(String mTitle, String mHours, String mRating, String mPlaceId, String mThumbnail, double mLat, double mLng) {
        this.mTitle = mTitle;
        this.mHours = mHours;
        this.mRating = mRating;
        this.mPlaceId = mPlaceId;
        this.mThumbnail = mThumbnail;
        this.mLat = mLat;
        this.mLng = mLng;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmHours() {
        return mHours;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmPlaceId() {
        return mPlaceId;
    }

    public double getmLat() {
        return mLat;
    }

    public double getmLng() {
        return mLng;
    }
}
