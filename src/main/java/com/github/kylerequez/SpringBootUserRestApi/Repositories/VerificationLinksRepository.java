package com.github.kylerequez.SpringBootUserRestApi.Repositories;

import com.github.kylerequez.SpringBootUserRestApi.Models.VerificationLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationLinksRepository extends JpaRepository<VerificationLink, String> {
}
