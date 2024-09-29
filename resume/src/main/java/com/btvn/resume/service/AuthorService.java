package com.btvn.resume.service;

import com.btvn.resume.dto.AuthorDTO;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.Author;
import com.btvn.resume.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthorService {
    Page<Author> findAll(int size, int page, String sortBy);
    Author findById(int id);
    List<Author> findByUsername(String name);
    Author save(Author user);
    void update(int id, AuthorDTO authorDTO);
    void deleteById(int id);
}
