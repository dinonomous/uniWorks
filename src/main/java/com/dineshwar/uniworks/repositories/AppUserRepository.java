package com.dineshwar.uniworks.repositories;

import com.dineshwar.uniworks.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    public AppUser findByUsername(String username);
    public AppUser findByEmail(String email);

}
