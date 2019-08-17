package com.example.seyaha;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    public String displayName;
    public String email;
    public String imageURL;
    public List<String> intrests;
    public boolean isAdmin;


    public User(String userDisplayName, String userEmail, String userImageURL, List<String> userIntrests, boolean isAdmin) {
        this.displayName = userDisplayName;
        this.email = userEmail;
        this.imageURL = userImageURL;
        this.intrests = userIntrests;
        this.isAdmin = isAdmin;

    }

    public User(String userDisplayName, String userImageURL) {
        this.displayName = userDisplayName;
        this.imageURL = userImageURL;
    }
}
