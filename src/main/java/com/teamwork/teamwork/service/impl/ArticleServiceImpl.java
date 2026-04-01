package com.teamwork.teamwork.service.impl;

import com.teamwork.teamwork.entity.Article;
import com.teamwork.teamwork.repository.ArticleRepository;
import com.teamwork.teamwork.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // CREATE or UPDATE article
    @Override
    public void saveArticle(Article article) {

        // Only set createdAt if it's a NEW article
        if (article.getId() == null) {
            article.setCreatedAt(LocalDateTime.now());
        }

        articleRepository.save(article);
    }

    // GET ALL ARTICLES (sorted by newest)
    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    // DELETE
    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    // Get single article
    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // ADD COMMENT
    @Override
    public void addComment(Long articleId, String comment) {

        Article article = articleRepository.findById(articleId).orElse(null);

        if (article != null) {

            // If comments list is null - create new list
            if (article.getComments() == null) {
                article.setComments(new ArrayList<>());
            }

            article.getComments().add(comment);

            articleRepository.save(article);
        }
    }
}