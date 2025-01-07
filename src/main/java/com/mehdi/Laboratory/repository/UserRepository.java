package com.mehdi.Laboratory.repository;

import com.mehdi.Laboratory.domain.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<JpaUser, Long> {

    Optional<JpaUser> findByUsername(String username);

}
