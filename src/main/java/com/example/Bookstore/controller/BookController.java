package com.example.Bookstore.controller;


import com.example.Bookstore.model.Book;
import com.example.Bookstore.repository.BookRepository;
import com.example.Bookstore.service.BookService;
import com.example.Bookstore.service.GoogleBooksApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService; // Rename properly: no "googleBooksService" anymore
    
    @Autowired
    private GoogleBooksApiService googleBooksApiService;

    @GetMapping("/api/books/enrich/{title}")
    public String enrichBookDetails(@PathVariable String title) {
        return googleBooksApiService.fetchBookDetails(title);
    }
    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Search books by title or author
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String q) {
        return bookService.searchBooks(q);
    }
}

	
	

	/*
	 * @Autowired private BookService bookService;
	 * 
	 * @Autowired private BookRepository bookRepository;
	 * 
	 * @GetMapping public List<Book> getAllBooks() { return
	 * bookService.getAllBooks(); }
	 * 
	 * @GetMapping("/{id}") public ResponseEntity<Book> getBookById(@PathVariable
	 * Long id) { Optional<Book> book = bookRepository.findById(id);
	 * 
	 * if (book.isPresent()) { return ResponseEntity.ok(book.get()); } else { return
	 * ResponseEntity.notFound().build(); } //return bookService.getBookById(id); }
	 * 
	 * @PostMapping public Book addBook(@RequestBody Book book) { return
	 * bookService.saveBook(book); }
	 * 
	 * @PutMapping("/{id}") public ResponseEntity<Book> updateBook(@PathVariable
	 * Long id, @RequestBody Book updatedBook) { Optional<Book> existingBook =
	 * bookRepository.findById(id); if (existingBook.isPresent()) { Book book =
	 * existingBook.get(); book.setTitle(updatedBook.getTitle());
	 * book.setAuthor(updatedBook.getAuthor());
	 * book.setPrice(updatedBook.getPrice()); return
	 * ResponseEntity.ok(bookRepository.save(book)); } else { return
	 * ResponseEntity.notFound().build(); } }
	 * 
	 * 
	 * @DeleteMapping("/{id}") public ResponseEntity<String>
	 * deleteBook(@PathVariable Long id) { if (bookRepository.existsById(id)) {
	 * bookRepository.deleteById(id); return
	 * ResponseEntity.ok("Book deleted successfully"); } else { return
	 * ResponseEntity.notFound().build(); } }
	 */

