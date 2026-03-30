package com.teamwork.teamwork.controller;

import com.teamwork.teamwork.entity.Article;
import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // show article page
    @GetMapping("/articles")
    public String articlePage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("articles", articleService.getAllArticles());
        model.addAttribute("article", new Article());

        return "articles";
    }

    // create article
    @PostMapping("/articles")
    public String createArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        article.setAuthor(user.getName());

        articleService.saveArticle(article);

        return "redirect:/articles";
    }
}