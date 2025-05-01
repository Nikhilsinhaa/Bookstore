package com.example.Bookstore.controller;

import com.example.Bookstore.model.Book;
import com.example.Bookstore.model.Order;
import com.example.Bookstore.model.User;
import com.example.Bookstore.service.BookService;
import com.example.Bookstore.service.OrderService;
import com.example.Bookstore.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
    	model.addAttribute("books", bookService.getAllBooks());
		/*
		 * List<Book> books = bookService.getAllBooks(); model.addAttribute("books",
		 * books);
		 */ // Critical line
        return "index";
    }
    @GetMapping("/order")
    public String order(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<Order> orders = orderService.getUserOrders(user);
        
        double totalSpent = orders.stream()
            .filter(o -> !"Canceled".equalsIgnoreCase(o.getStatus()))
            .mapToDouble(Order::getTotalPrice)
            .sum();
        
        model.addAttribute("orders", orders);
        model.addAttribute("totalSpent", totalSpent);
        return "order";
    }





    @PostMapping("/order/place/{bookId}")
    public String placeOrder(@PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/user/login";
        }

        Book book = bookService.getBookById(bookId);
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Book not found");
            return "redirect:/books";
        }

        // Use service to create and save order
        orderService.createOrder(user, book);

        redirectAttributes.addFlashAttribute("message", "Order placed successfully");
        return "redirect:/order";
    }
    @PostMapping("order/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        Order order = orderService.getOrderById(orderId);

        if (order != null) {
            orderService.deleteOrder(orderId);  // This deletes the order, you can also implement logic to mark it as canceled instead of deletion.
            redirectAttributes.addFlashAttribute("message", "Order canceled successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Order not found!");
        }

        return "redirect:/order";  // Redirect back to the order list
    }


    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String username = authentication.getName();  // Get logged-in username
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }
@GetMapping("/api/test-books")
@ResponseBody
public List<Book> testBooks() {
    return bookService.getAllBooks(); // Check this in browser/Postman
}

@GetMapping("/test")
public String test() {
    return "index"; // Verify template renders without security
}

}