package com.teamwork.teamwork.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT") // allows long content
    private String content;

    private String author;

    private LocalDateTime createdAt;


    //      Stores comments as a collection of strings.
//      Uses @ElementCollection so JPA stores it in a separate table automatically.
    @ElementCollection
    private List<String> comments = new ArrayList<>();

    public Article() {
    }

    public Article(String title, String content, String author, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.comments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    // Adds a single comment to the article
    public void addComment(String comment) {
        this.comments.add(comment);
    }
}