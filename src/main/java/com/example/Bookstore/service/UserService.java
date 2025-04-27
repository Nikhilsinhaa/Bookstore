package com.example.Bookstore.service;

import com.example.Bookstore.controller.LoginDTO;
import com.example.Bookstore.model.User;
import com.example.Bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
	 @Autowired
	    private UserRepository userRepository; // JPA repository for User

	    public User register(User user) {
	        // Example: Save the user to the database
	        return userRepository.save(user);
	    }
	    public User login(String username, String password) {
	        User user = userRepository.findByUsername(username);
	        if (user != null && user.getPassword().equals(password)) {
	            return user;
	        }
	        return null;
	    }


	    public ResponseEntity<?> login(LoginDTO loginDTO) {
	        // Handle login logic (validate user credentials)
	        User user = userRepository.findByUsername(loginDTO.getUsername());
	        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
	            return ResponseEntity.ok(user);
	        }
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }

	    public User getUserByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }
}

    /*@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
*/