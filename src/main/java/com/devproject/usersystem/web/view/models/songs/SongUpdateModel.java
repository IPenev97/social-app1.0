package com.devproject.usersystem.web.view.models.songs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class SongUpdateModel {
    private Long id;
    private String name;
    private String artist;
    private String songUrl;
}
