package org.example.librarymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Setter
@Getter
public class Patron {
    @Id
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
}
