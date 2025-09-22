package com.example.springboot.Features.Auth.repositories;

import com.example.springboot.SharedKernel.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.Map;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findByUsername(String username);

    @Query(value = """ 
                    SELECT acc.id as id, acc.username as username, acc.password as password, r.name as roleName
                    FROM Account acc
                    LEFT JOIN Role r ON r.id = acc.roleId
                    WHERE acc.username = :username
                    """, nativeQuery = true)
    Map<String, Object> findAccountRole(@Param("username") String username);

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
                    WHERE acc.username = :username
                    AND acc.id IS NOT NULL
                    GROUP BY acc.id, acc.username, r.name;
                    """, nativeQuery = true)
    Map<String, Object> findAccountRoleAndPermission(@Param("username") String username);
}
