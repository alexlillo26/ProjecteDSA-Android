package edu.upc.projecte;

public class Question {
    private String title;
    private String message;
    private String sender;

    public Question(String date, String title, String message, String sender) {
        this.title = title;
        this.message = message;
        this.sender = sender;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}