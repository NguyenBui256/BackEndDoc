package com.btvn.resume.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="books")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id = 0;

    @Column(name="book_title")
    private String bookTitle;

    @Column(name="publish_date")
    private Date publishDate;

    @Column(name="publisher")
    private String publisher;

//    @ManyToMany(mappedBy = "books")
//    private Set<Author> author = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    private Set<Library> libraries = new HashSet<>();
}
