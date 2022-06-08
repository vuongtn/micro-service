package com.example.demo.controllers;

import com.example.demo.models.Roles;
import com.example.demo.models.User;
import com.example.demo.models.UserRoles;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRolesRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    UserRepository getUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();
        if (strRoles == null) {
            Roles userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                        switch (role) {
                            case "admin":
                                Roles adminRole = roleRepository.findByName("ROLE_ADMIN")
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(adminRole);

                                break;
                            case "mod":
                                Roles modRole = roleRepository.findByName("ROLE_MODERATOR")
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(modRole);

                                break;
                            case "user":
                                Roles userRole = roleRepository.findByName("ROLE_USER")
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(userRole);
                                break;
                            default:
                                Roles defaultRole = roleRepository.findByName("ROLE_USER")
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                roles.add(defaultRole);
                        }
                    }
            );
        }
        userRepository.save(user);
        this.setData(user, roles);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }



    @PostMapping("/test1")
    public ResponseEntity<Boolean> registerUsers1(@RequestParam String name, @RequestBody SignupRequest user) {
        User user1 = new User();
        Set<String> strRoles = user.getRole();
        UserRoles a = new UserRoles();
        List<UserRoles> b = new ArrayList<>();
        Set<UserRoles> rolesSave = new HashSet<>();
        Set<Roles> roles = new HashSet<>();
        User userS = new User(user.getUsername(),
                user.getEmail(),
                encoder.encode(user.getPassword()));
        if (strRoles == null) {
            Roles userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }

        userRepository.save(userS);
        this.setData(userS, roles);

        return null;

    }

    private List<UserRoles> setData(User user, Set<Roles> role) {
        List<UserRoles> lst = new ArrayList<UserRoles>();
        role.stream().map(Roles::getId).forEach(roles -> {
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(user.getId());
            userRoles.setRoleId(roles);
            lst.add(userRoles);
        });
        userRolesRepository.saveAll(lst);
        return lst;
    }
}
