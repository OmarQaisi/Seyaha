package com.example.seyaha;

public class Comment {
    public User user;
    public String comment;
    public int rating;

    public Comment(User user, String comment, int rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }
}
