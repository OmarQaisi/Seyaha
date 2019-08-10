package com.example.seyaha;

import java.util.Map;

public class Tour {

    public String[] ImageURLs;
    public Map<String, String> placeNamesCategories;
    public double rating;
    public int comments;
    public String title, desc;


    public Tour(String[] imageURLs, Map<String, String> placeNamesCategories, double rating, int comments, String title, String desc) {
        ImageURLs = imageURLs;
        this.placeNamesCategories = placeNamesCategories;
        this.rating = rating;
        this.comments = comments;
        this.title = title;
        this.desc = desc;
    }

    public Tour(String[] imageURLs, double rating, int comments, String title, String desc) {
        ImageURLs = imageURLs;
        this.rating = rating;
        this.comments = comments;
        this.title = title;
        this.desc = desc;
    }
}
