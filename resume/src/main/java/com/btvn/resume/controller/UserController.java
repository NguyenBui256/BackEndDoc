package com.btvn.resume.controller;

import com.btvn.resume.dto.CustomResponse;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.User;
import com.btvn.resume.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public CustomResponse<Page<User>> findAll(
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return new CustomResponse<>(userService.findAll(size, page, sortBy),"User list.");
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable int id){
        return userService.findById(id);
    }

    @GetMapping("/users/{name}")
    public User findByUsername(@PathVariable String name) {
        return userService.findByUsername(name);
    }

    @GetMapping("/users/self-info")
    public CustomResponse<String> findSelfInfo(@RequestBody String token) throws ParseException, JOSEException, JsonProcessingException {
        return new CustomResponse<>(userService.findSelfInfo(token),"Your info");
    }

    @PostMapping("/open/signup")
    public CustomResponse<User> createUser(@RequestBody UserDTO userDto){
        User newUser = new User();
        BeanUtils.copyProperties(userDto, newUser);
        return new CustomResponse<>(userService.save(newUser),"User created.");
    }


    @PostMapping("/open/login")
    public CustomResponse<String> login(@RequestBody UserDTO userDto) throws JsonProcessingException {
        return new CustomResponse<>(userService.login(userDto.getUsername(), userDto.getPassword()), "Login success.");
    }

    @PostMapping("users/logout")
    public CustomResponse<String> logout(){
//        User newUser = new User();
//        BeanUtils.copyProperties(userDto, newUser);
//        return new CustomResponse<>(userService.save(newUser));
        return null;
    }

    @PutMapping("/users/{id}")
    public CustomResponse<String> updateUser(@RequestBody UserDTO userDTO, @PathVariable int id) {
        userService.update(id, userDTO);
        return new CustomResponse<>("User updated");
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse<String>  deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return new CustomResponse<>("User deleted");
    }
}
