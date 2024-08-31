package com.btvn.resume.service;

import com.btvn.resume.dto.BookDTO;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.Book;
import com.btvn.resume.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    Page<Book> findAll(int size, int page, String sortBy);
    Book findById(int id);
    List<Book> findByName(String name);
    Book save(Book user);
    void update(int id, BookDTO bookDTO);
    void deleteById(int id);
}
