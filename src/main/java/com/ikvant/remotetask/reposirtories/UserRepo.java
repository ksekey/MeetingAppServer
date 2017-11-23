package com.ikvant.remotetask.reposirtories;

import com.ikvant.remotetask.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long>{
    AppUser findByName(String name);
}
