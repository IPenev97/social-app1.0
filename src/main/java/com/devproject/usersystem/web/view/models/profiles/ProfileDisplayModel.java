package com.devproject.usersystem.web.view.models.profiles;

import com.devproject.usersystem.data.models.ImageModel;
import com.devproject.usersystem.data.models.Profile;
import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.data.models.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDisplayModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String country;
    private int age;
    private String gender;
    private String description;
    private ImageModel profilePicture;
    private List<Profile> friends;
    private List<Song> songs;

}
