package com.VoxPopuli.Users.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.VoxPopuli.Users.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByAlias(String alias);

    @Query("UPDATE User u SET u.passHash=:passHash WHERE u.userID=userId")
    void updatePassByUserId(@Param("user_id") UUID userId, @Param("pass_hash") String passHash);
}
