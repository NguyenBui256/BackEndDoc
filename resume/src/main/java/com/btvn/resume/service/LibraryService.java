package com.btvn.resume.service;

import com.btvn.resume.dto.LibraryDTO;
import com.btvn.resume.dto.UserDTO;
import com.btvn.resume.model.Book;
import com.btvn.resume.model.Library;
import com.btvn.resume.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LibraryService {
    Page<Library> findAll(int size, int page, String sortBy);
    Library findById(int id);
    List<Library> findByUsername(String name);
    Library save(Library library);
    void update(int id, LibraryDTO libraryDTO);
    void deleteById(int id);
}
