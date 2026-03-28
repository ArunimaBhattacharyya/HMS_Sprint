package com.example.hms.repository;

import com.example.hms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByBlock_BlockFloorAndBlock_BlockCode(int blockFloor, int blockCode);
    List<Room> findByRoomType(String roomType);
}