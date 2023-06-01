package com.github.kylerequez.SpringBootUserRestApi.Repositories;

import com.github.kylerequez.SpringBootUserRestApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    @Query("select u from User u " +
            "where " +
            "   u.contactNumber = :contactNumber " +
            "   or u.email = :email")
    Optional<User> findUserByContactNumberOrPassword(
            @Param(value = "contactNumber") String contactNumber,
            @Param(value = "email") String email
    );

    @Query("select u from User u " +
            "where " +
            "   u.id = :id")
    Optional<User> findUserById(@Param(value = "id") String id);

    @Query("select u from User u " +
            "where " +
            "   u.email = :email")
    Optional<User> findUserByEmail(@Param(value = "email") String email);
}
