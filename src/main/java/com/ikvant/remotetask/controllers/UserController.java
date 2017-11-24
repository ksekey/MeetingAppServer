package com.ikvant.remotetask.controllers;

import com.ikvant.remotetask.entities.AppUser;
import com.ikvant.remotetask.reposirtories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign_up")
    public ResponseEntity signUp(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("display_name") String displayName, @RequestParam("number") String number, @RequestParam("position") String position) {
        AppUser appUser = new AppUser();
        appUser.setName(name);
        appUser.setDisplayName(displayName);
        appUser.setPosition(position);
        appUser.setNumber(number);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
