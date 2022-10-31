package com.sideproject.sharingtimer.repository;

import com.sideproject.sharingtimer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByKakaoId(Long kakaoId);
}
