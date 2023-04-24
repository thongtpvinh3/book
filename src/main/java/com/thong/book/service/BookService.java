package com.thong.book.service;

import com.thong.book.entities.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);
    List<Book> findAll();
    Book findById(long id);
    Book update(long id, Book book);

    void delete(long id);
}
