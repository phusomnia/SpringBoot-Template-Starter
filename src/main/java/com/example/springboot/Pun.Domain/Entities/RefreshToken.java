package com.example.springboot.Pun.Domain.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "RefreshToken", schema = "spring")
public class RefreshToken {
    @Id
    @Size(max = 36)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Size(max = 255)
    @Column(name = "token")
    private String token;

    @Size(max = 36)
    @Column(name = "account_Id", length = 36)
    private String accountId;

    @Column(name = "expiryDate")
    private Instant expiryDate;

}