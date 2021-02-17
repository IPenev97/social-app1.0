package com.devproject.usersystem.web.view.models.songs;

import com.devproject.usersystem.data.models.ImageModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class SongUploadModel {
    private String name;
    private String artist;
    private String songUrl;
}
