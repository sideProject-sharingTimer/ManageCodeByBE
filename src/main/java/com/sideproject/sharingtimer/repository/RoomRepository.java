package com.sideproject.sharingtimer.repository;

import com.sideproject.sharingtimer.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> , RoomCustomRepository {

    //전체방 목록 조회
    List<Room> findAllByOrderByModifiedAt();
    //방 고유번호로 특정 방 조회
    Optional<Room> findByRoomId(String roomId);
}
