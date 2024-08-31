package com.btvn.resume.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="authors")
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id = 0;
    @Column(name="author_name")
    private String authorName;
    @Column(name="email")
    private String authorEmail;
    @Column(name="author_password")
    private String password;
//    @ManyToMany
//    private Set<Book> books = new HashSet<Book>();

    public Author(String authorName, String authorEmail, String password) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.password = password;
    }
}
