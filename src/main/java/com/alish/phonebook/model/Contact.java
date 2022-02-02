package com.alish.phonebook.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String company;
    private String githubId;

    @ElementCollection()
    @CollectionTable()
    private List<String> githubRepositories;
}