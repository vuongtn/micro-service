package com.example.demo.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER_ROLES", schema = "DEMO")
public class UserRoles  {

    private Long userId;
    private Long roleId;

    public UserRoles() {
    }

    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoles userRoles = (UserRoles) o;
        return userId == userRoles.userId &&
                roleId == userRoles.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
