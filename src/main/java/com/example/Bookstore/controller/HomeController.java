package com.example.Bookstore.controller;

import java.util.Arrays;
import java.util.List;
import com.example.Bookstore.model.Book;
import com.example.Bookstore.model.Order;
import com.example.Bookstore.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Bookstore.repository.BookRepository;
import com.example.Bookstore.service.BookService;
import com.example.Bookstore.service.OrderService;
import com.example.Bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books"; // renders books.html
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order"; // renders order.html
    }

    @GetMapping("/")
    public String home(Model model) {
    	List<Book> books = bookRepository.findAll(); // Fetch all books from the database
        model.addAttribute("books", books);
        return "index";
    }
    @PostMapping("/order/place/{bookId}")
    public String placeOrder(@PathVariable Long bookId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first to place an order.");
            return "redirect:/user/login";
        }

        // Place order logic
        Book book = bookService.getBookById(bookId);

        Order order = new Order();
        order.setUser(user);
        order.setBooks(Arrays.asList(book));
        order.setTotalPrice(book.getPrice());

        orderService.saveOrder(order);

        redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
        return "redirect:/order";
    }
        @GetMapping("/profile")
        public String profile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
            User user = (User) session.getAttribute("loggedInUser");

            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Please login to view your profile.");
                return "redirect:/user/login";
            }

            model.addAttribute("user", user);
            return "profile"; // Render profile.html
        
    }
   


}