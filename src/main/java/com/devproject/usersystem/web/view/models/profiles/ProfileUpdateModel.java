package com.devproject.usersystem.web.view.models.profiles;

import com.devproject.usersystem.data.models.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileUpdateModel {
    private String firstName;
    private String lastName;
    private String country;
    private int age;
    private String gender;
    private String description;
}
