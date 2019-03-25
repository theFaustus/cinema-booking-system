package com.evil.cbs.repository;

import com.evil.cbs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    @Query(nativeQuery = true, value = "update cbs.users set enabled=1 where id = :userId")
    @Modifying
    void enableUser(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "update cbs.users set enabled=0 where id = :userId")
    @Modifying
    void disableUser(@Param("userId") Long userId);
}
