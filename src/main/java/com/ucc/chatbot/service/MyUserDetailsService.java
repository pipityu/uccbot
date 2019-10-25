package com.ucc.chatbot.service;

import com.ucc.chatbot.model.User;
import com.ucc.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    //itt felülírjuk a UserDetailsService egyetlen metódusát. Ha nincs meg a user akkor exception
    // ha megvan akkor visszatér az emaillel passwordel és szerepkörökkel
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + userName + " not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthorities(user));
    }

    //Ez talán kiszedi vagyis kikeresi a jogokat a megtalált felhasználóból(amikkel rendelkezik)
    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
