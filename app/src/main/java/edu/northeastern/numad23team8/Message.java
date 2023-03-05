package edu.northeastern.numad23team8;

import edu.northeastern.numad23team8.models.User;

public class Message {
    private String message;
    private String username;
    private String time;

    public Message(String message, String username, String time) {
        this.message = message;
        this.username = username;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return username;
    }

    public void setUser(User user) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
