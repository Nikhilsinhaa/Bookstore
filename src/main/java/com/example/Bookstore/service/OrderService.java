package com.example.Bookstore.service;


import com.example.Bookstore.model.Book;
import com.example.Bookstore.model.Order;
import com.example.Bookstore.model.OrderItem;
import com.example.Bookstore.model.User;
import com.example.Bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
        
    }
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public double calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
    public Order createOrder(User user, Book book) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setBooks(List.of(book)); // If you're storing books directly

        // If using OrderItems (recommended)
        OrderItem item = new OrderItem();
        item.setBook(book);
        item.setQuantity(1);
        item.setPrice(book.getPrice());
        item.setOrder(order);

        Set<OrderItem> items = new HashSet<>();
        items.add(item);

        order.setOrderItems(items);
        order.setTotalPrice(book.getPrice());

        return orderRepository.save(order);
    }
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            orderRepository.save(order);
        }
    }


}