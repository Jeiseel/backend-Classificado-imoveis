package com.dsc.housemarket.Services;

import com.dsc.housemarket.Models.User;
import com.dsc.housemarket.Repository.UserRepository;
import com.dsc.housemarket.RepositoryImplementation.UserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    /*
    private final UserRepository userDao;

    @Autowired
    public CustomUserDetailService(UserRepository userDao) {
        this.userDao = userDao;
    }
     */

    @Autowired
    private UserRepositoryImplementation userRepositoryImplementation;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        Optional<User> userOptional = Optional.ofNullable(userDao.findByEmail(username)).orElseThrow(() -> new UsernameNotFoundException("User not Found"));

         */

        Optional<User> existsUser = userRepositoryImplementation.findByEmail(username);

        System.out.println(existsUser.get().getEmail());

        User user = null;

        if(!existsUser.equals(Optional.empty())) {
            user = existsUser.get();
        }

        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isAdmin() ? authorityListAdmin : authorityListUser);
    }
}
