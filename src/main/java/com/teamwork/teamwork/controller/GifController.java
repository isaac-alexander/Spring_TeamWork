package com.teamwork.teamwork.controller;

import com.teamwork.teamwork.entity.Gif;
import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.service.GifService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GifController {

    private GifService gifService;

    public GifController(GifService gifService) {
        this.gifService = gifService;
    }

    @GetMapping("/gifs")
    public String gifPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("gifs", gifService.getAllGifs());

        return "gifs";
    }

    @PostMapping("/gifs")
    public String createGif(@ModelAttribute Gif gif,
                            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        gif.setAuthor(user.getName());

        gifService.saveGif(gif);

        return "redirect:/gifs";
    }
}