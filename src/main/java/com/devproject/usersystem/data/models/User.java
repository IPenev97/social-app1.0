package com.devproject.usersystem.data.models;

import com.devproject.usersystem.data.models.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
    private String role;
    private boolean active;




}
