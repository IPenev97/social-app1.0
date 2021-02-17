package com.devproject.usersystem.data.repositories;

import com.devproject.usersystem.data.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Profile getByUsername(String username);
}
