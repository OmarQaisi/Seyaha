package com.example.seyaha;

import java.net.URL;

public class OverviewItem
{
    public String placeName;
    public String category;
    public URL placePic;

    public OverviewItem(String placeName,String category,URL placePic)
    {
        this.placeName=placeName;
        this.category=category;
        this.placePic=placePic;
    }

}
