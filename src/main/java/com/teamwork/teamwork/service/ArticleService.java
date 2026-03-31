package com.teamwork.teamwork.service;

import com.teamwork.teamwork.entity.Article;

import java.util.List;

public interface ArticleService {

    void saveArticle(Article article);           // Create or update an article

    List<Article> getAllArticles();              // List all articles (newest first)

    void deleteArticle(Long id);                 // Delete article by id

    Article getArticleById(Long id);             // Get article by id

    void addComment(Long articleId, String comment); // Add a comment to article
}
