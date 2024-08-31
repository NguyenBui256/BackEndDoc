package com.btvn.resume.controller;

import com.btvn.resume.dto.AuthorDTO;
import com.btvn.resume.dto.CustomResponse;
import com.btvn.resume.dto.LibraryDTO;
import com.btvn.resume.model.Author;
import com.btvn.resume.model.Library;
import com.btvn.resume.model.User;
import com.btvn.resume.service.AuthorService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("")
    public CustomResponse<Page<Author>> findAll(
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return new CustomResponse<>(authorService.findAll(size, page, sortBy));
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable int id){
        return authorService.findById(id);
    }

    @GetMapping("/{name}")
    public List<Author> findByName(@PathVariable String name) {
        return authorService.findByName(name);
    }

    @PostMapping("")
    public Author createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author newAuthor = new Author(authorDTO.getAuthorName(), authorDTO.getEmail(), authorDTO.getPassword());
        return authorService.save(newAuthor);
    }

    @PutMapping("/{id}")
    public CustomResponse<String> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO authorDTO) {
        authorService.update(id, authorDTO);
        return new CustomResponse<>("Author updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse<String> deleteAuthor(@PathVariable int id) {
        authorService.deleteById(id);
        return new CustomResponse<>("Author deleted");
    }


}
