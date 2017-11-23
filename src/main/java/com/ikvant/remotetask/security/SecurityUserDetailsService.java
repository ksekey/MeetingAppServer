package com.ikvant.remotetask.security;

import com.ikvant.remotetask.entities.AppUser;
import com.ikvant.remotetask.reposirtories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by ikvant on 24.08.17.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByName(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(appUser.getName(), appUser.getPassword(), list);
    }
}
