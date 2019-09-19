package com.example.seyaha;

import java.io.Serializable;

public class ActivityClass implements Serializable {

    public String nameAR;
    public String nameEN;
    public int cost;
    public double time;

    public ActivityClass(String nameAr, String nameEn, int cost, double time) {
        this.nameAR = nameAr;
        this.nameEN = nameEn;
        this.cost = cost;
        this.time = time;
    }

    public ActivityClass() {
    }

}
