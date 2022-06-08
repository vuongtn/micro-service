package com.example.demo.security.services;


import com.example.demo.models.Roles;
import com.example.demo.models.User;
import com.example.demo.models.UserRoles;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRolesRepository;
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
