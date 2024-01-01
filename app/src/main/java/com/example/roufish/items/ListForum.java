package com.example.roufish.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListForum {

    private String forumText;
    private String userId;
    private String username;
    private Date timestamp;

    public ListForum() {
        // Required empty constructor for Firestore
    }

    public ListForum(String forumText,String userId,String username, Date timestamp) {
        this.forumText = forumText;
        this.username = username;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }
    public String getForumText() {
        return forumText;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserId() {
        return userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}