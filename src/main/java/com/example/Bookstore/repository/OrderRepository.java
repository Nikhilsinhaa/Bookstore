package com.example.Bookstore.repository;

import com.example.Bookstore.model.Order;
import com.example.Bookstore.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userid);
	List<Order> findByUser(User user);
}
