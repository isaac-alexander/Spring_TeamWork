package com.teamwork.teamwork.service;

import com.teamwork.teamwork.entity.Article;

import java.util.List;

public interface ArticleService {

    void saveArticle(Article article);

    List<Article> getAllArticles();

}
