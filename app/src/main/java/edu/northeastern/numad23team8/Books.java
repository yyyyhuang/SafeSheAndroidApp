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

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return "Author: " + author;
    }

    public String getAuthorYear() {
        return "Author Year: " + birthYear + " - " + deathYear;
    }

    public String getLanguage() {
        return "Language: " + language;
    }
}
