package com.example.Bookstore.controller;

import com.example.Bookstore.model.User;
import com.example.Bookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String username,
        @RequestParam String password,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        User user = userService.login(username, password);
        if(user != null) {
            session.setAttribute("loggedInUser", user);
            System.out.println("User stored in session: " + user.getUsername());  // Add this line
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("error", "Invalid credentials");
        return "redirect:/user/login";
    }

    @PostMapping("/register")
    public String register(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String email,
        RedirectAttributes redirectAttributes
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);

        userService.register(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful!");
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
