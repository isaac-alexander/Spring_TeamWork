package com.teamwork.teamwork.repository;

import com.teamwork.teamwork.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Returns articles sorted by newest first
    List<Article> findAllByOrderByCreatedAtDesc();
}
