package com.btvn.resume.service;

import com.btvn.resume.dao.AuthorRepository;
import com.btvn.resume.dao.UserRepository;
import com.btvn.resume.dto.AuthorDTO;
import com.btvn.resume.model.Author;
import com.btvn.resume.model.NotFoundException;
import com.btvn.resume.model.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Page<Author> findAll(int size, int page, String sortBy) {
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.min(Math.max(size,1),20), Sort.Direction.ASC, sortBy);
        List<Author> userList = authorRepository.findAll();
        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public Author findById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with the given ID."));
    }

    @Override
    public List<Author> findByUsername(String name) {
        return List.of();
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void update(int id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
               .orElseThrow(() -> new NotFoundException("Author not found with the given ID."));

        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorEmail(authorDTO.getEmail());
        authorRepository.save(author);
    }

    @Override
    public void deleteById(int id) {
        Optional<Author> project = authorRepository.findById(id);
        if (project.isEmpty()) throw new NotFoundException("Project not found with the given ID.");
        authorRepository.delete(project.get());
    }
}
