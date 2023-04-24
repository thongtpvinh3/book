package com.thong.book.controller;

import com.thong.book.common.ResponseObject;
import com.thong.book.entities.Book;
import com.thong.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/find-all")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable long id) {
        return bookService.findById(id);
    }

    @PostMapping("/create")
    public Book create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PatchMapping("/update/{id}")
    public Book update(@PathVariable long id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        bookService.delete(id);
        return ResponseEntity.ok(new ResponseObject("delete ok", HttpStatus.OK));
    }
}
