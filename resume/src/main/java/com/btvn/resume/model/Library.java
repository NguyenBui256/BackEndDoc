package com.btvn.resume.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="libraries")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "library_name")
    private String name;
    @Column(name = "address")
    private String address;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="libraries_books",
    joinColumns = @JoinColumn(name="libraries_id"),
    inverseJoinColumns = @JoinColumn(name="books_id"))
    private Set<Book> books = new HashSet<>();
}
