package com.example.seyaha;

import java.util.List;

public class User {

    public String displayName;
    public String email;
    public String imageURL;
    public List<String> intrests;
    public boolean isAdmin;
    public List<String> toursCommentedOn;
    public String userId;


    public User(String userDisplayName, String userEmail, String userImageURL, List<String> userIntrests, boolean isAdmin, List<String> toursCommentedOn, String userId) {
        this.displayName = userDisplayName;
        this.email = userEmail;
        this.imageURL = userImageURL;
        this.intrests = userIntrests;
        this.isAdmin = isAdmin;
        this.toursCommentedOn = toursCommentedOn;
        this.userId = userId;
    }

    public User() { }

    public User(String userDisplayName, String userImageURL) {
        this.displayName = userDisplayName;
        this.imageURL = userImageURL;
    }

}
