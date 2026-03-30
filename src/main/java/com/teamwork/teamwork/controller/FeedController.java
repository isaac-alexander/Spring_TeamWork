package com.teamwork.teamwork.controller;

import com.teamwork.teamwork.entity.Article;
import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.service.ArticleService;
import com.teamwork.teamwork.service.GifService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private GifService gifService;


    @GetMapping("/feed")
    public String feedPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("articles", articleService.getAllArticles());
        model.addAttribute("gifs", gifService.getAllGifs());

        return "feed";
    }

}
