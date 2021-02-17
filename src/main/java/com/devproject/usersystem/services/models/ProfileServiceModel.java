package com.devproject.usersystem.services.models;

import com.devproject.usersystem.data.models.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileServiceModel {
    private String username;
    private String firstName;
    private String lastName;
    private String country;
    private int age;
    private Gender gender;
    private String description;

}
