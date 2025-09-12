package com.gipit.bookshop_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleID;

    @Column(name="role_name", length=100)
    private String roleName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(name="user_roles",
               joinColumns = @JoinColumn(name="role_id"),
                inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> listUsers;
}
