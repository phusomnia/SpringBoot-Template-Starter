package com.example.springboot.Features.AuthAPI.Account;

import com.example.springboot.Entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findByUsername(String username);

    @Query(value = """ 
                    SELECT acc.id as id, acc.username as username, acc.password as password, r.name as roleName
                    FROM Account acc
                    LEFT JOIN Role r ON r.id = acc.roleId
                    WHERE acc.username = ?
                    """, nativeQuery = true)
    Optional<Map<String, Object>> findAccountRole(String username);

    @Query(value = """ 
                    SELECT
                        acc.id AS id,
                        acc.username AS username,
                        acc.password AS password,
                        r.name AS roleName,
                        GROUP_CONCAT(p.name SEPARATOR ', ') AS permissionList
                    FROM Account acc
                             LEFT JOIN Role r ON r.id = acc.roleId
                             LEFT JOIN RolePermission rp ON rp.roleId = r.id
                             LEFT JOIN Permission p ON p.id = rp.permissionId
                    WHERE acc.username = ?
                    GROUP BY acc.id, acc.username, r.name;
                    """, nativeQuery = true)
    Optional<Map<String, Object>> findAccountRoleAndPermission(String username);
}
