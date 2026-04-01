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
    public String articlePage(HttpSession session, Model model) {

        // Get currently logged-in user from session.
        User user = (User) session.getAttribute("loggedInUser");

        // If no user logged in, redirect to login page.
        if (user == null) return "redirect:/login";

        // Send logged-in user to html.
        model.addAttribute("user", user);

        // calls service to get all articles and send to html
        model.addAttribute("articles", articleService.getAllArticles());

        return "articles";
    }

    // create article
    @PostMapping("/articles")
    public String createArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        article.setAuthor(user.getName()); // sets logged-in user as author.
        articleService.saveArticle(article); // save new article.

        return "redirect:/articles"; // refresh page to show new article.
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
        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        if (!article.getAuthor().equals(user.getName())
                && !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        // calls service to get all articles and send to html
        model.addAttribute("article", article);

        // Send logged-in user to html.
        model.addAttribute("user", user);

        return "edit-article";
    }

    // update article
    @PostMapping("/articles/update")
    public String updateArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        Article existingUser = articleService.getArticleById(article.getId());
        if (existingUser == null) return "redirect:/articles";

        // Only author or admin
        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        if (!existingUser.getAuthor().equals(user.getName())
                && !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        // Keep original author
        article.setAuthor(existingUser.getAuthor());
        article.setCreatedAt(existingUser.getCreatedAt());

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
        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        if (!article.getAuthor().equals(user.getName())
                && !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        articleService.deleteArticle(id);

        return "redirect:/articles";
    }

    // add comment
    @PostMapping("/articles/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String comment,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) return "redirect:/login";

        comment = user.getName() + ": " + comment;
        // adds username to comment.

        articleService.addComment(id, comment);
        // calls service to save comment.

        return "redirect:/articles"; // Refresh page
    }

    // view single article
    @GetMapping("/articles/{id}") // View single article
    public String viewArticle(@PathVariable Long id,
                              Model model,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) return "redirect:/login";

        Article article = articleService.getArticleById(id);

        if (article == null) return "redirect:/articles";

        model.addAttribute("article", article); // Pass article to html
        model.addAttribute("user", user); // Pass user to html

        return "view-article";
    }
}
