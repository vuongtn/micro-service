package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.Roles;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.UserRoles;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

  @Autowired
  RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      List<Roles> roles = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        List<UserRoles> rolesSet = userRolesRepository.findByUserId(user.getId());


      roles = roleRepository.findAllById(user.getId());
        return UserDetailsImpl.build(user, roles);
    }

}
