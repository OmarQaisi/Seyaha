package com.example.seyaha;

public class Comment {
    public User user;
    public String comment;
    public float rating;

    public Comment(User user, String comment, float rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }
    public  Comment(){};
}
