package com.netcracker.vacations.service;

import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.repository.UserRepository;
import com.netcracker.vacations.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public AppUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByLogin(username).get(0);
        if (user != null) {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
//            return new User(user.getLogin(), user.getPassword(), grantedAuthorities);
            return new MyUserPrincipal(user);
        }
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

}
