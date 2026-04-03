package com.teamwork.teamwork.controller;

import com.teamwork.teamwork.entity.Gif;
import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.service.GifService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GifController {

    // calls the gif service
    private final GifService gifService;

    public GifController(GifService gifService) {
        this.gifService = gifService;
    }

    // show gif page
    @GetMapping("/gifs")
    public String gifPage(HttpSession session, Model model) {

        // gets logged-in user from session
        User user = (User) session.getAttribute("loggedInUser");
        // if no user redirect to login page
        if (user == null) return "redirect:/login";

        // Send user to html
        model.addAttribute("user", user);
        return "gifs";
    }

    // create gif page
    @PostMapping("/gifs")
    public String createGif(@RequestParam("title") String title, // get title from form
                            @RequestParam("file") MultipartFile file,  // get uploaded file
                            HttpSession session) {

        // get logged-in user
        User user = (User) session.getAttribute("loggedInUser");

        if (title == null || title.trim().isEmpty()) {
            return "redirect:/gifs";
        }
        // if no file selected - go back to gifs page
        if (file == null || file.isEmpty()) {
            return "redirect:/gifs";
        }

        // create new Gif object
        Gif gif = new Gif();

        // set title from form
        gif.setTitle(title);

        // set author as logged-in user's name
        gif.setAuthor(user.getName());

        //  save gif uploads to cloudinary and database
        gifService.saveGif(gif, file);

        // redirect to feed to see all gifs
        return "redirect:/feed";
    }

    //  delete gif
    @DeleteMapping("/gifs/delete/{id}")
    public String deleteGif(@PathVariable Long id, HttpSession session) {

        // get user from session
        User user = (User) session.getAttribute("loggedInUser");

        // get gif from database
        Gif gif = gifService.getGifById(id);

        // if not found - go back
        if (gif == null) return "redirect:/gifs";

        // !existingUser.getAuthor() checks if the person who originally created the article (author) is the same as the current logged-in user (user.getName()).
        // !"admin".equalsIgnoreCase(user.getRole())
        // checks if the current user’s role is not "admin".
        // check permission: only author or admin can delete
        if (!gif.getAuthor().equals(user.getName()) &&
                !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/gifs";
        }

        // delete gif (cloudinary and DB)
        gifService.deleteGif(id);

        return "redirect:/feed";
    }

    // add comment
    @PostMapping("/gifs/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String comment,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        // attach username to comment
        comment = user.getName() + ": " + comment;

        // save comment using service
        gifService.addComment(id, comment);

        return "redirect:/feed";
    }

    //  view single gif
    @GetMapping("/gifs/{id}") // URL like /gifs/1
    public String viewGif(@PathVariable Long id, Model model, HttpSession session) {

        // get user
        User user = (User) session.getAttribute("loggedInUser");

        // if not logged in - login
        if (user == null) return "redirect:/login";

        // get gif from database
        Gif gif = gifService.getGifById(id);
        if (gif == null) return "redirect:/feed";

        // send gif to HTML
        model.addAttribute("gif", gif);

        // send user to HTML
        model.addAttribute("user", user);

        // return view-gif.html
        return "view-gif";
    }
}
