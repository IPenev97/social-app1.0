package com.devproject.usersystem.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String author;
    private String imgURL;
    @ManyToMany(mappedBy = "books",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Profile> books;
}
