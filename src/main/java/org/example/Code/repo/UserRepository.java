package org.example.Code.repo;

import org.example.Code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods if needed
}
