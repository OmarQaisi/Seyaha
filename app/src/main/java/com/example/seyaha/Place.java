package com.example.seyaha;

public class Place {


    public String categoryAR;
    public String categoryEN;
    public int cost;
    public String descAR;
    public String descEN;
    public int estimatedTime;
    public String imageURL;
    public int internet;
    public double latitude;
    public double longitude;
    public String nameAR;
    public String nameEN;
    public int recommendedAge;
    public int recommendedSeason;
    public int recommendedTime;


    public Place(String categoryAR, String categoryEN, int cost, String descAR, String descEN, int estimatedTime, String imageURL, int internet, double latitude, double longitude, String nameAR, String nameEN, int recommendedAge, int recommendedSeason, int recommendedTime) {
        this.categoryAR = categoryAR;
        this.categoryEN = categoryEN;
        this.cost = cost;
        this.descAR = descAR;
        this.descEN = descEN;
        this.estimatedTime = estimatedTime;
        this.imageURL = imageURL;
        this.internet = internet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.recommendedAge = recommendedAge;
        this.recommendedSeason = recommendedSeason;
        this.recommendedTime = recommendedTime;
    }
}
