package com.teamwork.teamwork.service.impl;

import com.teamwork.teamwork.entity.Article;
import com.teamwork.teamwork.repository.ArticleRepository;
import com.teamwork.teamwork.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void saveArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }
}
