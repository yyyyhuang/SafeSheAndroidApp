package edu.northeastern.numad23team8.models;

public class Friend {
    private String first_name, last_name;
    private int number, emergency_contact;

    public Friend(String first_name, String last_name, int number, int emergency_contact){
        this.first_name = first_name;
        this.last_name = last_name;
        this.number = number;
        this.emergency_contact = emergency_contact;
    }

    public Friend(){}

    public String getFirst_name(){return this.first_name;}
    public String getLast_name(){return this.last_name;}
    public int getNumber(){return this.number;}
    public int getEmergency_contact(){return this.emergency_contact;}
}
