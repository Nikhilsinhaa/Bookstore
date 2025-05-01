package com.example.Bookstore.controller;

import com.example.Bookstore.model.Book;
import com.example.Bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
        
        @GetMapping("/books")
        public String showBooks(Model model) {
            model.addAttribute("title", "Book Collection");
            model.addAttribute("books", bookService.getAllBooks());
            return "books"; 
        }
    }


