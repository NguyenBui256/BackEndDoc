package com.btvn.resume.service;

import com.btvn.resume.dao.BookRepository;
import com.btvn.resume.dao.LibraryRepository;
import com.btvn.resume.dto.AuthorDTO;
import com.btvn.resume.dto.LibraryDTO;
import com.btvn.resume.model.Author;
import com.btvn.resume.model.Book;
import com.btvn.resume.model.Library;
import com.btvn.resume.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{
    private final LibraryRepository libraryRepository;

    @Override
    public Page<Library> findAll(int size, int page, String sortBy) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1),20), Sort.Direction.ASC, sortBy);
        List<Library> userList = libraryRepository.findAll();
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public Library findById(int id) {
        return libraryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Library not found with the given ID."));
    }

    @Override
    public List<Library> findByUsername(String name) {
        return List.of();
    }

    @Override
    public Library save(Library library) {
        return libraryRepository.save(library);
    }

    public void update(int id, LibraryDTO libraryDTO) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Library not found with the given ID."));
        library.setName(libraryDTO.getName());
        library.setAddress(libraryDTO.getAddress());
        libraryRepository.save(library);
    }

    @Override
    public void deleteById(int id) {
        Optional<Library> project = libraryRepository.findById(id);
        if (project.isEmpty()) throw new NotFoundException("Library not found with the given ID.");
        libraryRepository.delete(project.get());
    }
}
