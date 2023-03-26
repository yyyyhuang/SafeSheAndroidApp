package edu.northeastern.numad23team8.models;

public class Friend {
    private String username, email;
    private int emergency_contact;

    public Friend(String username, String email){
        this.username = username;
        this.email = email;
    }
    public Friend(String username, int emergency_contact, String email){
        this.username = username;
        this.emergency_contact = emergency_contact;
        this.email = email;
    }

    public Friend(){}

    public void setEmergency_contact(int number){this.emergency_contact = number;}

    public String getEmail(){return this.email;}
    public String getUsername(){return this.username;}
    public int getEmergency_contact(){return this.emergency_contact;}
}
