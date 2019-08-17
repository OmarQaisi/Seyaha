package com.example.seyaha;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import java.util.List;

public class Tour {

    public String titleAR;
    public String titleEN;
    public String categoriesAR;
    public String categoriesEN;
    public String[] imageURLs;
    public List<Place> places;
    public double ratingsNum;
    public int commentsNum;
    public List<Comment> comments;

    public Tour(String titleAR, String titleEN, String descAR, String descEN, String[] imageURLs, List<Place> places, double ratingsNum, int commentsNum, List<Comment> comments) {
        this.titleAR = titleAR;
        this.titleEN = titleEN;
        this.categoriesAR = descAR;
        this.categoriesEN = descEN;
        this.imageURLs = imageURLs;
        this.places = places;
        this.ratingsNum = ratingsNum;
        this.commentsNum = commentsNum;
        this.comments = comments;
    }

    public Tour(String[] imagesURL, double rating, int comment, String title, String desc){
        imageURLs = imagesURL;
        ratingsNum = rating;
        commentsNum = comment;
        titleEN = title;
        categoriesEN = desc;
    }

    public String makeCategories(String[] categories)
    {
        String result="";
        for(int i=0;i<categories.length;i++)
        {
            if(i==0) {
                result += i;
            }
            else if(i==categories.length-1)
            {
                result+="and "+i+".";
            }
            else
            {
                result+=", "+i;
            }
        }
        return result;
    }

    public static void addClickEffect(View view)
    {
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(500);
        view.startAnimation(animation1);
    }

}
