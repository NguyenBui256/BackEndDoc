package com.btvn.resume.controller;

import com.btvn.resume.dto.CustomResponse;
import com.btvn.resume.model.User;
import com.btvn.resume.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public String findById(int id){
        User theUser = userService.findById(id);
        return "info";
    }

    @PostMapping("")
    public User createUser(@RequestBody User theUser){
        theUser.setId(0);
        return userService.save(theUser);
    }

    @PutMapping("")
    public User updateProject(@RequestBody User theUser) {
        return userService.save(theUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable int id) {
        userService.deleteById(id);
    }
}
