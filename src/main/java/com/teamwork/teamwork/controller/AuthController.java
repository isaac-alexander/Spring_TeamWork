package com.teamwork.teamwork.controller;

import com.teamwork.teamwork.entity.User;
import com.teamwork.teamwork.exception.EmailAlreadyExistsException;
import com.teamwork.teamwork.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // default route
    @GetMapping("/")
    public String goHome() {
        return "redirect:/login";
    }

    // show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // handle login
    @PostMapping("/login-user")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {

        User user = userService.findByEmail(email);

        // if user is not null and user password = password
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/feed";
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // show register page (only admin)
    @GetMapping("/register")
    public String registerPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        // if user is null and not an admin redirect to login page
        // checks if the current user’s role is not "admin".
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "register";
    }

    // handle registration with duplicate email check
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               Model model,
                               HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        user.setRole("user"); // default role for employees

        try {
            userService.saveUser(user); // may throw EmailAlreadyExistsException
            return "redirect:/feed";

        } catch (EmailAlreadyExistsException ex) {
            // Catch duplicate email and display error in form
            model.addAttribute("error", ex.getMessage()); // send error message to html
            model.addAttribute("user", user); // Pass user to html
            return "register";
        }
    }

    // handle logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session
        return "redirect:/login";
    }
}