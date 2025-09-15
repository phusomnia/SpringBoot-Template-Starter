package com.example.springboot.Features.AuthAPI.Role;

import com.example.springboot.Entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RefreshToken, String> {
    
}
