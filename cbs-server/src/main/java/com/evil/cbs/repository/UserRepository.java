package com.evil.cbs.repository;

import com.evil.cbs.domain.Ticket;
import com.evil.cbs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Modifying
    @Query("update User u set u.enabled = :enabledValue where u.email = :email")
    void updateEnabledValue(@Param("email") String email, @Param("enabledValue") int value);

}
