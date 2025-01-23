package com.example.Bookstore.controller;


import com.example.Bookstore.model.User;
import com.example.Bookstore.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
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
	
}
