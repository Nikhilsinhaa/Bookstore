package com.example.Bookstore.controller;

import java.util.List;
import com.example.Bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Bookstore.repository.BookRepository;

@Controller
public class HomeController {
	
	@Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
    	List<Book> books = bookRepository.findAll(); // Fetch all books from the database
        model.addAttribute("books", books);
        return "index";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Render the register.html file
    }
    @GetMapping("/books")
    public String booksPage() {
        return "books"; // Render the books.html file
    }
}