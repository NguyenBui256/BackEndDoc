package com.btvn.resume.controller;

import com.btvn.resume.dto.CustomResponse;
import com.btvn.resume.dto.LibraryDTO;
import com.btvn.resume.model.Book;
import com.btvn.resume.model.Library;
import com.btvn.resume.model.User;
import com.btvn.resume.service.BookService;
import com.btvn.resume.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("")
    public CustomResponse<Page<Library>> findAll(
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return new CustomResponse<>(libraryService.findAll(size, page, sortBy));
    }

    @GetMapping("/{id}")
    public Library findById(@PathVariable int id){
        return libraryService.findById(id);
    }

    @GetMapping("/{name}")
    public List<Library> findByName(@PathVariable String name) {
        return libraryService.findByName(name);
    }

    @PostMapping("")
    public Library createLibrary(@RequestBody @Valid LibraryDTO libraryDTO) {
        Library library = new Library();
        BeanUtils.copyProperties(libraryDTO, library);
        return libraryService.save(library);
    }

    @PutMapping("/{id}")
    public CustomResponse<String> updateLibrary(@PathVariable int id, @RequestBody LibraryDTO libraryDTO) {
        libraryService.update(id, libraryDTO);
        return new CustomResponse<>("Library updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse<String> deleteLibrary(@PathVariable int id) {
        libraryService.deleteById(id);
        return new CustomResponse<>("Library deleted");
    }
}
