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

    private final GifService gifService;

    public GifController(GifService gifService) {
        this.gifService = gifService;
    }

    @GetMapping("/gifs")
    public String gifPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("gifs", gifService.getAllGifs());
        return "gifs";
    }

    @PostMapping("/gifs")
    public String createGif(@ModelAttribute Gif gif, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        gif.setAuthor(user.getName());
        gifService.saveGif(gif);

        return "redirect:/gifs";
    }

    @GetMapping("/gifs/edit/{id}")
    public String editGif(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Gif gif = gifService.getGifById(id);
        if (gif == null) return "redirect:/gifs";

        if (!gif.getAuthor().equals(user.getName()) && !"admin".equalsIgnoreCase(user.getRole()))
            return "redirect:/gifs";

        model.addAttribute("gif", gif);
        return "edit-gif";
    }

    @PostMapping("/gifs/update")
    public String updateGif(@ModelAttribute Gif gif, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Gif existing = gifService.getGifById(gif.getId());
        if (existing == null) return "redirect:/gifs";

        if (!existing.getAuthor().equals(user.getName()) && !"admin".equalsIgnoreCase(user.getRole()))
            return "redirect:/gifs";

        gif.setAuthor(existing.getAuthor());
        gifService.saveGif(gif);

        return "redirect:/gifs";
    }

    @PostMapping("/gifs/delete/{id}")
    public String deleteGif(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Gif gif = gifService.getGifById(id);
        if (gif == null) return "redirect:/gifs";

        if (!gif.getAuthor().equals(user.getName()) && !"admin".equalsIgnoreCase(user.getRole()))
            return "redirect:/gifs";

        gifService.deleteGif(id);
        return "redirect:/gifs";
    }

    @PostMapping("/gifs/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String comment,
                             HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        comment = user.getName() + ": " + comment;
        gifService.addComment(id, comment);

        return "redirect:/gifs";
    }

    // view singlr gif
    @GetMapping("/gifs/{id}")
    public String viewGif(@PathVariable Long id,
                          Model model,
                          HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Gif gif = gifService.getGifById(id);
        if (gif == null) return "redirect:/gifs";

        model.addAttribute("gif", gif);
        model.addAttribute("user", user);

        return "view-gif";
    }
}