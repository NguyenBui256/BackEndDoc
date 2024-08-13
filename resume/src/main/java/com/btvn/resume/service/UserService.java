package com.btvn.resume.service;

import com.btvn.resume.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> findAll(int size, int page, String sortBy);
    User findById(int id);
    User save(User user);
    void deleteById(int id);
}
