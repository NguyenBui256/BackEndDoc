package com.btvn.resume.service;

import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface UserService {
    Page<User> findAll(int size, int page, String sortBy);
    User findById(int id);
    User findByUsername(String name);
    User save(User user);
    String findSelfInfo(String token) throws ParseException, JOSEException, JsonProcessingException;
    void update(int id, UserDTO user);
    void deleteById(int id);
    String login(String username, String password) throws JsonProcessingException;
    void logout();
}
