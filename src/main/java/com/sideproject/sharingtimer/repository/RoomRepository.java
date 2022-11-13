package com.sideproject.sharingtimer.repository;

import com.sideproject.sharingtimer.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> , RoomCustomRepository {

    List<Room> findAllByOrderByModifiedAt();

}
