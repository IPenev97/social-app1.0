package com.devproject.usersystem.data.repositories;

import com.devproject.usersystem.data.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {
    Optional<ImageModel> findByName(String name);

}
