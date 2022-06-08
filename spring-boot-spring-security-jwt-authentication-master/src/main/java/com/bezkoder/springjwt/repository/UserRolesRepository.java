package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    List<UserRoles> findByUserId(Long UserId);

}
