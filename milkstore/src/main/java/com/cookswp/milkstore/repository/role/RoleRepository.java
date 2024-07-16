package com.cookswp.milkstore.repository.role;

import com.cookswp.milkstore.pojo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
