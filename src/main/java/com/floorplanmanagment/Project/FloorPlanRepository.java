package com.floorplanmanagment.Project;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FloorPlanRepository extends CrudRepository<Room,Integer>{

    @Modifying
    @Query("INSERT INTO Room (id, status, capacity, timestamp) VALUES (:#{#room.id}, :#{#room.status}, :#{#room.capacity}, :#{#room.timestamp})")
    void saveInfo(@Param("room") Room room);

    @Query(value = "SELECT * FROM room WHERE id = :id ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Room getById(@Param("id")int id);

    @Query(value = "WITH RankedRoom AS ( SELECT room.*, ROW_NUMBER() OVER (PARTITION BY id ORDER BY TIMESTAMP DESC) AS RowRank FROM room )SELECT id,STATUS,capacity,TIMESTAMP FROM RankedRoom WHERE RowRank = 1;",nativeQuery = true)
    ArrayList<Room> getAllRooms();
    
}
