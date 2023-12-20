package com.ofektom.springsecclass.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofektom.springsecclass.dto.UserDto;
import com.ofektom.springsecclass.enums.Role;
import com.ofektom.springsecclass.model.Users;
import com.ofektom.springsecclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Function<UserDto, Users> saveUser = (userDto -> {
        Users users = new ObjectMapper().convertValue(userDto, Users.class);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Role.USER);
        return userRepository.save(users);
    });
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

}
