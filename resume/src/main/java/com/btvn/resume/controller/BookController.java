package com.btvn.resume.controller;

import com.btvn.resume.dto.AuthorDTO;
import com.btvn.resume.dto.BookDTO;
import com.btvn.resume.dto.CustomResponse;
import com.btvn.resume.model.Author;
import com.btvn.resume.model.Book;
import com.btvn.resume.model.Library;
import com.btvn.resume.model.User;
import com.btvn.resume.service.AuthorService;
import com.btvn.resume.service.BookService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public CustomResponse<Page<Book>> findAll(
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return new CustomResponse<>(bookService.findAll(size, page, sortBy));
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable int id){
        return bookService.findById(id);
    }

    @GetMapping("/{name}")
    public List<Book> findByName(@PathVariable String name) {
        return bookService.findByName(name);
    }

    @PostMapping("")
    public Book createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book newBook = new Book();
        BeanUtils.copyProperties(bookDTO, newBook);
        return bookService.save(newBook);
    }

    @PutMapping("/{id}")
    public CustomResponse<String> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        bookService.update(id, bookDTO);
        return new CustomResponse<>("Book updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse<String> deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return new CustomResponse<>("Book deleted");
    }
}
