package com.example.Bookstore.controller;


import com.example.Bookstore.model.User;
import com.example.Bookstore.repository.UserRepository;
import com.example.Bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class AuthController {
	
	@Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // register.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userService.login(username, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            redirectAttributes.addFlashAttribute("success", "Login successful!");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/user/login";
        }
    }


    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        userService.register(newUser);

        session.setAttribute("loggedInUser", newUser);
        redirectAttributes.addFlashAttribute("success", "Registration successful!");

        return "redirect:/";
    }

}

	/*@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public String register(
	@RequestParam String username,
    @RequestParam String password,
    @RequestParam String email
) {
    // Check if username or email already exists
    if (userRepository.findByUsername(username).isPresent()) {
        return "Error: Username already exists";
    }
    if (userRepository.findByEmail(email).isPresent()) {
        return "Error: Email already exists";
    }

    // Save the new user
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    userRepository.save(user);

    return "User registered successfully";
}

	@GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }
		// Login endpoint
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
	    Optional<User> user = userRepository.findByUsername(username);
	    if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
	        // Success
	    	return "redirect:/";
	    } else {
	        // Failure
	    	model.addAttribute("error", "Invalid username or password");
	        return "login";
	    }
	}
	// Auth check endpoint to see if the user is logged in
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok(new AuthStatus(false));
        }
        
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new AuthStatus(true, user.getUsername()));
    }
    
 // Inner class to return the auth status (logged in or not)
    public static class AuthStatus {
        private boolean loggedIn;
        private String username;

        public AuthStatus(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }

        public AuthStatus(boolean loggedIn, String username) {
            this.loggedIn = loggedIn;
            this.username = username;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public String getUsername() {
            return username;
        }
    }
	
}
*/