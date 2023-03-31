package edu.northeastern.numad23team8.models;

public class Friend {
    private String username, email, userKey, imageUrl;
    private long emergency_contact, number;

    public Friend(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
        this.setImageUrl(" ");
        this.setEmergency_contact(0);
        this.setNumber(0);
    }

    public Friend(){}

    public void setNumber(long number){this.number = number;}
    public void setUsername(String name){this.username = name;}
    public void setEmail(String email){this.email = email;}
    public void setUserKey(String key){this.userKey = key;}
    public void setImageUrl(String url){this.imageUrl = url;}
    public void setEmergency_contact(long number){this.emergency_contact = number;}

    public String getUserKey(){return this.userKey;}
    public String getImageUrl(){return this.imageUrl;}
    public String getEmail(){return this.email;}
    public String getUsername(){return this.username;}
    public long getEmergency_contact(){return this.emergency_contact;}
    public long getNumber(){return this.number;}
}
