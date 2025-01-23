package com.example.Bookstore.controller;


import com.example.Bookstore.model.Book;
import com.example.Bookstore.model.Order;
import com.example.Bookstore.repository.OrderRepository;
import com.example.Bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping("/view")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "order";
    }


    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
    @PostMapping("/place")
    public Order placeOrder(@RequestBody List<Long> bookIds) {
        List<Book> selectedBooks = bookRepository.findAllById(bookIds); // Fetch selected books
        double totalPrice = selectedBooks.stream().mapToDouble(Book::getPrice).sum(); // Calculate total price

        Order order = new Order();
        order.setBooks(selectedBooks);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order); // Save the order in the database
    }

}