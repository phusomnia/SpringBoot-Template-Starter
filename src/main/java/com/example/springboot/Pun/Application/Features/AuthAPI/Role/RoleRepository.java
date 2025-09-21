package com.example.springboot.Pun.Application.Features.AuthAPI.Role;

import com.example.springboot.Pun.Domain.Entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RefreshToken, String> {
    
}
