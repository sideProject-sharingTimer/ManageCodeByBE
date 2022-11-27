package com.sideproject.sharingtimer.repository;


import com.sideproject.sharingtimer.model.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimerRepository  extends JpaRepository<Timer,Long>, TimerCustomRepository{

    Optional<Timer> findByRoomIdAndUserId(String roomId, Long userId);

}
