package org.example.schroniskodlapsow.repository.user;

import org.example.schroniskodlapsow.entity.user.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByEmail(String email);
}
