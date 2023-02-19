package edu.northeastern.numad23team8;

public class Books {
    private String title, author, birthYear, deathYear, language;

    public Books(String title, String author, String birthYear, String deathYear, String language) {
        this.title = title;
        this.author = author;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(String deathYear) {
        this.deathYear = deathYear;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
