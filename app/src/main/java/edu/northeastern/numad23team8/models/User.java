package edu.northeastern.numad23team8.models;

public class User {
    private String username;
    private int count;

    public User(String username, int count) {
        this.username = username;
        this.count = count;
    }

    public String getUsername() {return this.username;}

    public int getCount() {return this.count;}
}
