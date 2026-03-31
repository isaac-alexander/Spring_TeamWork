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

    // view article
    @GetMapping("/articles")
    public String allArticles(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("articles", articleService.getAllArticles());

        return "articles";
    }

    // create article
    @PostMapping("/articles")
    public String createArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        article.setAuthor(user.getName());

        articleService.saveArticle(article);

        return "redirect:/articles";
    }

    // edit page
    @GetMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable Long id,
                              Model model,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Article article = articleService.getArticleById(id);
        if (article == null) return "redirect:/articles";

        // Only author or admin
        if (!article.getAuthor().equals(user.getName())
                && !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        model.addAttribute("article", article);
        model.addAttribute("user", user);

        return "edit-article";
    }

    // update article
    @PostMapping("/articles/update")
    public String updateArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Article existing = articleService.getArticleById(article.getId());
        if (existing == null) return "redirect:/articles";

        // Only author or admin
        if (!existing.getAuthor().equals(user.getName())
                && !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        // Keep original author
        article.setAuthor(existing.getAuthor());

        articleService.saveArticle(article);

        return "redirect:/articles";
    }

//    delete article
    @PostMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Article article = articleService.getArticleById(id);
        if (article == null) return "redirect:/articles";

        // Only author or admin
        if (!article.getAuthor().equals(user.getName())
                && !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        articleService.deleteArticle(id);

        return "redirect:/articles";
    }

    // add comment
    @PostMapping("/articles/comment/{id}")
    public String commentArticle(@PathVariable Long id,
                                 @RequestParam("comment") String comment,
                                 HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        // Save comment with username
        articleService.addComment(id, user.getName() + ": " + comment);

        return "redirect:/articles";
    }

   // view single aarticle
    @GetMapping("/articles/{id}")
    public String viewArticle(@PathVariable Long id,
                              Model model,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Article article = articleService.getArticleById(id);
        if (article == null) return "redirect:/articles";

        model.addAttribute("article", article);
        model.addAttribute("user", user);

        return "view-article";
    }
}
