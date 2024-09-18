package com.example.taskmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "user_tbl")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String email;

    private String password;

    private String phoneNumber;

    @Transient
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_tbl", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    })
    private List<Role> roles;

    @Transient
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Task> tasks;

    public User() {
        this.login = "";
        this.email = "";
        this.password = "";
        this.phoneNumber = "";
        this.roles = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public User(final String login, final String email, final String password, final String phoneNumber,
            final List<Role> roles, final List<Task> tasks) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.tasks = tasks;
    }

    public void removeItem(final Long itemId) {
        Task task = this.tasks.stream().filter(t -> t.getId() == itemId).findFirst().orElse(null);
        if (task != null) {
            this.tasks.remove(task);
            task.setUser(null);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

}
