package com.EShop.ecommerce_backend.Repositories;



import com.EShop.ecommerce_backend.Model.AppRole;
import com.EShop.ecommerce_backend.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}

