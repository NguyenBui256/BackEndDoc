package com.btvn.resume.service;

import com.btvn.resume.dao.UserRepository;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.NotFoundException;
import com.btvn.resume.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public Page<User> findAll(int size, int page, String sortBy) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1),20), Sort.Direction.ASC, sortBy);
        List<User> userList = userRepository.findAll();
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with the given ID."));
    }

    @Override
    public List<User> findByName(String name) {
        return List.of();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void update(int id, UserDTO user) {

    }

    @Override
    public void deleteById(int id) {
        Optional<User> project = userRepository.findById(id);
        if (project.isEmpty()) throw new NotFoundException("User not found with the given ID.");
        userRepository.delete(project.get());
    }
}
