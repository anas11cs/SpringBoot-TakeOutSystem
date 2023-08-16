package com.tkxel.takeoutsystem.service.impl;

import com.tkxel.takeoutsystem.entity.AppUserDetails;
import com.tkxel.takeoutsystem.entity.User;
import com.tkxel.takeoutsystem.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class AppUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userObject = userRepository.findByUserName(username);
        userObject.orElseThrow(()-> new UsernameNotFoundException("Not Found: " +  username));
        return userObject.map(AppUserDetails:: new).get();
    }
}
