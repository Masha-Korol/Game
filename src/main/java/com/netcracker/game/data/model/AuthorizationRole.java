package com.netcracker.game.data.model;

import com.netcracker.game.data.model.enums.ERoleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user_authorization_roles", schema = "public")
public class AuthorizationRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "roles_seq", allocationSize = 1)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ERoleType roleName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public ERoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(ERoleType roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationRole role = (AuthorizationRole) o;
        return roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    @Override
    public String toString() {
        return "AuthorizationRole{" +
                "roleId=" + roleId +
                ", roleName=" + roleName +
                '}';
    }
}
