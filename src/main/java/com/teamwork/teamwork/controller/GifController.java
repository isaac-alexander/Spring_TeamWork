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

    // calls the gif service. final means it does not change
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
        // sends user object to html
        model.addAttribute("user", user);
        // calls service to get all gifs and send to html
        model.addAttribute("gifs", gifService.getAllGifs());
        return "gifs";
    }

    // create gif page
    @PostMapping("/gifs") // triggered when form submits
    public String createGif(
            @RequestParam("title") String title, // get title from form
            @RequestParam("file") MultipartFile file, // get uploaded file
            HttpSession session) {

        // get logged-in user
        User user = (User) session.getAttribute("loggedInUser");

        // if not logged in - go to login
        if (user == null)
            return "redirect:/login";

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

        // call service → upload to cloudinary + save to DB
        gifService.saveGif(gif, file);

        // redirect back to gifs page
        return "redirect:/gifs";
    }

    //  delete gif
    @PostMapping("/gifs/delete/{id}") // triggered when delete button clicked
    public String deleteGif(@PathVariable Long id, HttpSession session) {

        // get user from session
        User user = (User) session.getAttribute("loggedInUser");

        // if not logged in - login
        if (user == null) return "redirect:/login";

        // get GIF from database
        Gif gif = gifService.getGifById(id);

        // if not found - go back
        if (gif == null) return "redirect:/gifs";

        // check permission: only author or admin can delete
        if (!gif.getAuthor().equals(user.getName()) &&
                !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/gifs";
        }

        // delete GIF (cloudinary + DB)
        gifService.deleteGif(id);

        return "redirect:/gifs";
    }

    //  add comment
    @PostMapping("/gifs/comment/{id}")
    public String addComment(
            @PathVariable Long id, // GIF ID
            @RequestParam String comment, // comment text
            HttpSession session) {

        // get logged-in user
        User user = (User) session.getAttribute("loggedInUser");

        // if not logged in → login
        if (user == null) return "redirect:/login";

        // attach username to comment
        comment = user.getName() + ": " + comment;

        // save comment using service
        gifService.addComment(id, comment);

        return "redirect:/gifs";
    }

    //  view single gif
    @GetMapping("/gifs/{id}") // URL like /gifs/1
    public String viewGif(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        // get user
        User user = (User) session.getAttribute("loggedInUser");

        // if not logged in - login
        if (user == null) return "redirect:/login";

        // get GIF from database
        Gif gif = gifService.getGifById(id);

        // if not found → go back
        if (gif == null) return "redirect:/gifs";

        // send gif to HTML
        model.addAttribute("gif", gif);

        // send user to HTML
        model.addAttribute("user", user);

        // return view-gif.html
        return "view-gif";
    }
}
