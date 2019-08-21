package com.example.seyaha;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import java.util.List;

public class Tour {

    public List<String> categoriesAR;
    public List<String> categoriesEN;
    public List<Comment> comments;
    public int commentsNum;
    public List<String> imageURLs;
    public List<Place> places;
    public double ratingsNum;
    public String titleAR;
    public String titleEN;


    public Tour(List<String> categoriesAR, List<String> categoriesEN, List<Comment> comments, int commentsNum, List<String> imageURLs, List<Place> places, double ratingsNum, String titleAR, String titleEN) {
        this.categoriesAR = categoriesAR;
        this.categoriesEN = categoriesEN;
        this.comments = comments;
        this.commentsNum = commentsNum;
        this.imageURLs = imageURLs;
        this.places = places;
        this.ratingsNum = ratingsNum;
        this.titleAR = titleAR;
        this.titleEN = titleEN;
    }

    public Tour() {}

    public String makeEnglishDescription(List<String> categoriesEN)
    {
        String result="Categories: ";
        for(int i=0;i<categoriesEN.size();i++)
        {
            if(i==0) {
                result += categoriesEN.get(i);
            }
            else if(i==categoriesEN.size()-1)
            {
                result+=" and "+categoriesEN.get(i)+".";
            }
            else
            {
                result+=", "+categoriesEN.get(i);
            }
        }
        return result;
    }

    public String makeArabicDescription(List<String> categoriesAR)
    {
        String result="";
        for(int i=0;i<categoriesAR.size();i++)
        {
            if(i==0) {
                result += categoriesAR.get(i);
            }
            else if(i==categoriesAR.size()-1)
            {
                result+=" و "+categoriesAR.get(i)+".";
            }
            else
            {
                result+=" ,"+categoriesAR.get(i);
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
