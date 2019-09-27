package com.example.seyaha;

import com.google.firebase.firestore.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Comment {
    public User user;
    public String comment;
    public float rating;
    public String time;

    public Comment(User user, String comment, float rating, String time) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
        this.time = time;
    }

    public Comment() { }

}
