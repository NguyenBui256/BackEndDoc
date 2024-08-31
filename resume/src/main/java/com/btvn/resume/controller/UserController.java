package com.btvn.resume.controller;

import com.btvn.resume.dto.CustomResponse;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.User;
import com.btvn.resume.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public CustomResponse<Page<User>> findAll(
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return new CustomResponse<>(userService.findAll(size, page, sortBy),"User list.");
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id){
        return userService.findById(id);
    }

    @GetMapping("/{name}")
    public List<User> findByName(@PathVariable String name) {
        return userService.findByName(name);
    }

    @PostMapping("")
    public User createUser(@RequestBody UserDTO userDto){
        User newUser = new User();
        BeanUtils.copyProperties(userDto, newUser);
        return userService.save(newUser);
    }

    @PutMapping("/{id}")
    public CustomResponse<String> updateUser(@RequestBody UserDTO userDTO, @PathVariable int id) {
        userService.update(id, userDTO);
        return new CustomResponse<>("User updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse<String>  deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return new CustomResponse<>("User deleted");
    }
}
