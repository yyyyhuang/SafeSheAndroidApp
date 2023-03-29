package edu.northeastern.numad23team8.models;

public class Friend {
    private String username, email, userKey, imageUrl;
    private int emergency_contact;

    public Friend(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
        this.setImageUrl(" ");
        this.setEmergency_contact(0);
    }

    public Friend(){}

    public void setUsername(String name){this.username = name;}
    public void setEmail(String email){this.email = email;}
    public void setUserKey(String key){this.userKey = key;}
    public void setImageUrl(String url){this.imageUrl = url;}
    public void setEmergency_contact(int number){this.emergency_contact = number;}

    public String getUserKey(){return this.userKey;}
    public String getImageUrl(){return this.imageUrl;}
    public String getEmail(){return this.email;}
    public String getUsername(){return this.username;}
    public int getEmergency_contact(){return this.emergency_contact;}
}
