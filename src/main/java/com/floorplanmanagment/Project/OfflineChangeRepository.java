package com.floorplanmanagment.Project;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OfflineChangeRepository extends CrudRepository<Room,Integer> {
    @Modifying
    @Query("INSERT INTO Room (id, status, capacity, timestamp) VALUES (:#{#room.id}, :#{#room.status}, :#{#room.capacity}, :#{#room.timestamp})")
    void saveInfo(@Param("room") Room room);

    @Query(value = "SELECT * FROM room WHERE id = :id ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Room getById(@Param("id")int id);

    ArrayList<Room> findAll();
}
