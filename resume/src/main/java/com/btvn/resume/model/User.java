package com.btvn.resume.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "dob")
    private String dob;
    @Column(name = "gender")
    private String gender;
    @Column(name = "location")
    private String location;
    @Column(name = "avatar_url")
    private String avatarUrl;
}
