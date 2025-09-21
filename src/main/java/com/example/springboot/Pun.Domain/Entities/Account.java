package com.example.springboot.Pun.Domain.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Account", schema = "spring")
public class Account {
    @Id
    @Size(max = 36)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "roleId")
//    private com.example.springboot.Entity.Role role;

}