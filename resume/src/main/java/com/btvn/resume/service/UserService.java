package com.btvn.resume.service;

import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> findAll(int size, int page, String sortBy);
    User findById(int id);
    List<User> findByName(String name);
    User save(User user);
    void update(int id, UserDTO user);
    void deleteById(int id);
}
