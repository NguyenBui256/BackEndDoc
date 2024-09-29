package com.btvn.resume.service;

import com.btvn.resume.dao.AuthorRepository;
import com.btvn.resume.dao.BookRepository;
import com.btvn.resume.dto.BookDTO;
import com.btvn.resume.model.Author;
import com.btvn.resume.model.Book;
import com.btvn.resume.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Page<Book> findAll(int size, int page, String sortBy) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1),20), Sort.Direction.ASC, sortBy);
        List<Book> userList = bookRepository.findAll();
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with the given ID."));
    }

    @Override
    public List<Book> findByUsername(String name) {
        return List.of();
    }

    @Override
    public Book save(Book author) {
        return bookRepository.save(author);
    }

    public void update(int id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with the given ID."));

        book.setBookTitle(bookDTO.getBookTitle());
        book.setPublisher(bookDTO.getPublisher());
        bookRepository.save(book);
    }

    @Override
    public void deleteById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) throw new NotFoundException("Book not found with the given ID.");
        bookRepository.delete(book.get());
    }
}
