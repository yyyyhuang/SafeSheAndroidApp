package edu.northeastern.numad23team8.models;

public class User {
    private String username;

    //private String userid;
    private int count_0;
    private int count_1;
    private int count_2;
    private int count_3;
    private int count_4;
    private int count_5;


    public User(String username,  int count_0, int count_1, int count_2, int count_3, int count_4, int count_5) {
        this.username = username;
        this.count_0 = count_0;
        this.count_1 = count_1;
        this.count_2 = count_2;
        this.count_3 = count_3;
        this.count_4 = count_4;
        this.count_5 = count_5;
    }

    public User(){}

    public String getUsername() {return this.username;}

    //public String getUserid() {return this.userid;}
    public int getCount_0() {return this.count_0;}
    public int getCount_1() {return this.count_1;}
    public int getCount_2() {return this.count_2;}
    public int getCount_3() {return this.count_3;}
    public int getCount_4() {return this.count_4;}
    public int getCount_5() {return this.count_5;}
}
