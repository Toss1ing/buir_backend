package com.example.taskmanager.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "role_tbl")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @JsonIgnore
    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
        this.roleName = "";
    }

    public Role(final String roleName, final List<User> users) {
        this.roleName = roleName;
        this.users = users;
    }
}
