package com.example.Bookstore.controller;


import com.example.Bookstore.model.Order;
import com.example.Bookstore.model.User;
import com.example.Bookstore.repository.OrderRepository;
import com.example.Bookstore.repository.UserRepository;
import com.example.Bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	 @Autowired
	    private UserService userService;

	    @PostMapping("/register")
	    public ResponseEntity<User> register(@RequestBody User user) {
	        return ResponseEntity.ok(userService.register(user));
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
	        return userService.login(loginDTO);
	    }

	    @GetMapping("/{username}")
	    public ResponseEntity<User> getUser(@PathVariable String username) {
	        return ResponseEntity.ok(userService.getUserByUsername(username));
	    }
	}



    /*@Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return ResponseEntity.ok(new ProfileResponse(user.getUsername(), user.getEmail(), orders));
    }

    static class ProfileResponse {
        private String username;
        private String email;
        private List<Order> orders;

        public ProfileResponse(String username, String email, List<Order> orders) {
            this.username = username;
            this.email = email;
            this.orders = orders;
        }

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public List<Order> getOrders() {
			return orders;
		}

		public void setOrders(List<Order> orders) {
			this.orders = orders;
		}
        
        
        
        // Getters and Setters
    }
}*/

