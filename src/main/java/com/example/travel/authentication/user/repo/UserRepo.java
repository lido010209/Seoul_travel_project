package com.example.travel.authentication.user.repo;

import com.example.travel.authentication.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.authorities WHERE u.id= :id")
    Optional<UserEntity> userWithAuthority(@Param("id") Long id);
}
