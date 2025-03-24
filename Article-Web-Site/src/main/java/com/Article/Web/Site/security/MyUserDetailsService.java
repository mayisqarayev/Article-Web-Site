package com.Article.Web.Site.security;

import com.Article.Web.Site.model.UserEntity;
import com.Article.Web.Site.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findUserEntityByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found"));
        return MyUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .isEnabled(user.isEnabled())
                .isAccountNonExpired(user.isAccountNonExpired())
                .isAccountNonLocked(user.isAccountNonLocked())
                .isCredentialsNonExpired(user.isCredentialsNonExpired())
                .authority(user.getAuthority()).build();
    }
}
