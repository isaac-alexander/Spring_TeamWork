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

        return "articles";
    }

    // create article
    @PostMapping("/articles")
    public String createArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        article.setAuthor(user.getName()); // sets logged-in user as author.
        articleService.saveArticle(article); // save new article.

        // redirect to feed to show all articles
        return "redirect:/feed";
    }

    // edit article page
    @GetMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable Long id,
                              Model model,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        // get article by id
        Article article = articleService.getArticleById(id);
        if (article == null) return "redirect:/articles";

        // Only author or admin
        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        if (!article.getAuthor().equals(user.getName()) &&
                !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        // Send article and user to html
        model.addAttribute("article", article);
        model.addAttribute("user", user);

        return "edit-article";
    }

    // update article
    @PutMapping("/articles/update")
    public String updateArticle(@ModelAttribute Article article,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        // Fetch existing article from database
        Article existingArticle = articleService.getArticleById(article.getId());
        if (existingArticle == null) return "redirect:/articles";

        // Only author or admin
        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        if (!existingArticle.getAuthor().equals(user.getName()) &&
                !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        // Keep original author and createdAt
        article.setAuthor(existingArticle.getAuthor());
        article.setCreatedAt(existingArticle.getCreatedAt());

        // save updated article
        articleService.saveArticle(article);

        // redirect to feed
        return "redirect:/feed";
    }

    //    delete article
    @DeleteMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        // get article from database
        Article article = articleService.getArticleById(id);
        if (article == null) return "redirect:/articles";

        // Only author or admin
        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        if (!article.getAuthor().equals(user.getName()) &&
                !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/articles";
        }

        // delete article
        articleService.deleteArticle(id);

        // redirect to feed
        return "redirect:/feed";
    }

    // add comment to article
    @PostMapping("/articles/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String comment,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) return "redirect:/login";

        // add username to comment
        comment = user.getName() + ": " + comment;

        // save comment using service
        articleService.addComment(id, comment);

        // redirect to feed
        return "redirect:/feed";
    }

    // view single article
    @GetMapping("/articles/{id}")
    public String viewArticle(@PathVariable Long id, Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        // get article
        Article article = articleService.getArticleById(id);
        if (article == null) return "redirect:/feed";

        // send article and user to html
        model.addAttribute("article", article);
        model.addAttribute("user", user);

        return "view-article";
    }
}