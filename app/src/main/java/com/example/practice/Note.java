package com.example.practice;

public class Note {
    private int id;
    private String title;
    private String text;
    private String deadline;
    private boolean done;

    public Note(int id, String title, String text, String deadline, boolean done) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.deadline = deadline;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }
}
