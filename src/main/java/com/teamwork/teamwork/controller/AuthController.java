package com.teamwork.teamwork.controller;

import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    // show login page
    @GetMapping("/")
    public String defaultRoute() {
        return "login";
    }

    // show login page
    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login";
    }

    // show register page
    @GetMapping("/register") // handles GET request to /register
    public String registerPage(Model model) {

        model.addAttribute("user", new User()); // sends empty object to HTML

        return "register"; // must match register.html file name
    }

    // handle registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

        userService.saveUser(user); // save to DB

        return "redirect:/login";
    }

    // handle login
    @PostMapping("/do-login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {

        User user = userService.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {

            // store logged-in user in session
            session.setAttribute("loggedInUser", user);

            return "redirect:/feed";
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }
}