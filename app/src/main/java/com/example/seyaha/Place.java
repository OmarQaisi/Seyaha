package com.example.seyaha;

public class Place {
    public String nameAR;
    public String nameEN;
    public String descAR;
    public String descEN;
    public String categoryAR;
    public String categoryEN;
    public String coordinates;
    public String imageURL;
    public int cost;
    public int internet;
    public int airQuality;
    public int recommendedEstimation;
    public int recommendedSeason;
    public int recommendedTime;
    public int recommendedAge;

    public Place(String nameAR, String nameEN, String descAR, String descEN, String categoryAR, String categoryEN, String coordinates, String imageURL, int cost, int internet, int airQuality, int recommendedEstimation, int recommendedSeason, int recommendedTime, int recommendedAge) {
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.descAR = descAR;
        this.descEN = descEN;
        this.categoryAR = categoryAR;
        this.categoryEN = categoryEN;
        this.coordinates = coordinates;
        this.imageURL = imageURL;
        this.cost = cost;
        this.internet = internet;
        this.airQuality = airQuality;
        this.recommendedEstimation = recommendedEstimation;
        this.recommendedSeason = recommendedSeason;
        this.recommendedTime = recommendedTime;
        this.recommendedAge = recommendedAge;
    }
}
