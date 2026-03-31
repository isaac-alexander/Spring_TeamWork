package com.teamwork.teamwork.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Gif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imageUrl; // URL or path to the uploaded GIF

    private String author;

    private LocalDateTime createdAt;

//    Stores comments on the GIF as strings
    @ElementCollection
    private List<String> comments = new ArrayList<>();

    public Gif() {}

    public Gif(String title, String imageUrl, String author, LocalDateTime createdAt) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.author = author;
        this.createdAt = createdAt;
        this.comments = new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getComments() { return comments; }
    public void setComments(List<String> comments) { this.comments = comments; }

    public void addComment(String comment) {
        this.comments.add(comment);
    }
}