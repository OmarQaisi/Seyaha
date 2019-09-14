package com.example.seyaha;

import java.io.Serializable;

public class ActivityClass implements Serializable
{

    public String nameAr;
    public String nameEn;
    public int cost;
    public double time;

    public ActivityClass(String nameAr, String nameEn, int cost, double time){
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.cost = cost;
        this.time = time;
    }
    public ActivityClass(){
    }

}
