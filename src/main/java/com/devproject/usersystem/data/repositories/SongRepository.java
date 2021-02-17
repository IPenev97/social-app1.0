package com.devproject.usersystem.data.repositories;

import com.devproject.usersystem.data.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song,Long> {
    Song findByNameAndAndArtist(String name, String artist);
}
