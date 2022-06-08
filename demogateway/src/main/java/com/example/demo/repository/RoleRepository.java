package com.example.demo.repository;

import com.example.demo.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
  Optional<Roles> findByName(String name);

  List<Roles> findAllById(Long id);



}
