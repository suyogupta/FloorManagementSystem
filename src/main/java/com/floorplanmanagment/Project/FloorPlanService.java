package com.floorplanmanagment.Project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
// import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.floorplanmanagment.Project.com.floorplanmanagment.Project.Booking;

import jakarta.annotation.PostConstruct;

@Component("floorPlanService")
public class FloorPlanService {

    @Autowired
    private FloorPlanRepository floorPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfflineSyncService offlineSyncService;


    private Map<Integer, Room> roomsByroomId = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, User> usersByuserId = Collections.synchronizedMap(new HashMap<>());

    @PostConstruct
    public void cache()
    {
        // synchronizeOfflineChanges();
        ArrayList<Room> allRooms = floorPlanRepository.getAllRooms();
        ArrayList<User> allUsers = userRepository.findAll();
        Map<Integer,Room> newMap = new HashMap<>();
        for (Room room : allRooms) {
            newMap.put(room.getId(), room);
        }
        Map<Integer,User> userMap = new HashMap<>();
        for (User user : allUsers) {
            userMap.put(user.getId(), user);
        }
        roomsByroomId = Collections.synchronizedMap(newMap);
        usersByuserId = Collections.synchronizedMap(userMap);

    }

    public ArrayList<Room> getAll()
    {
        ArrayList<Room> result = floorPlanRepository.getAllRooms();
        return result;
    }

    @Transactional
    public String savefloorPlan(Room room)
    {
        room.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Room existingRoom = roomsByroomId.get(room.getId());
        if (existingRoom!=null && existingRoom.getTimestamp().after(room.getTimestamp())) {
            
            return "The floor plan has been updated more recently by another user";
        }
        floorPlanRepository.saveInfo(room);
        roomsByroomId.put(room.getId(),room);
        return "SUCCESS";
    }

    @Transactional
    public void saveOfflineChange(Room room)
    {
        Room existingRoom = roomsByroomId.get(room.getId());
        if (existingRoom!=null && existingRoom.getTimestamp().after(room.getTimestamp())) {
            
            return;
        }
        floorPlanRepository.saveInfo(room);
        roomsByroomId.put(room.getId(),room);
    }

    public Room getRoomById(int num)
    {
        return floorPlanRepository.getById(num);
    }    

    public Room getRoomByRoomId(int num)
    {
        return roomsByroomId.get(num);
    }

    @Transactional
    public int getRoomAllocation(Booking booking)
    {
        int num = booking.getNum();
        Room room = null;

        for (Integer Id : roomsByroomId.keySet()) {
            Room thisRoom = roomsByroomId.get(Id);
            if(thisRoom.getStatus()==Constants.AVAILABLE)
            {
                if(room != null)
                {
                    if(thisRoom.getCapacity()<room.getCapacity() && thisRoom.getCapacity()>=num){
                        room = thisRoom;
                    }
                    else if(room.getCapacity()==thisRoom.getCapacity())
                    {
                        if(thisRoom.getTimestamp().before(room.getTimestamp()))
                        {
                            room = thisRoom;
                        }
                    }
                }
                else
                {
                    if (thisRoom.getCapacity()>=num) {
                        room = thisRoom;
                    }
                }
            }
        }

        
        if(room==null)
        {
            return -1;
        }
        else
        {
            updateCache(room);
            return room.getId();
        }

    }

    @Transactional
    public void updateCache(Room room)
    {
        room.setStatus(Constants.UNAVAILABLE);
        savefloorPlan(room);
    }

    public String validateUser(int userId, String password)
    {
        User user = usersByuserId.get(userId);
        if(user==null)
        {
            return Constants.INVALID_USER;
        }
        else if(!password.equals(user.getPassword()))
        {
            return Constants.INVALID_PASSWORD;
        }
        else{
            return user.getName();
        }
    }

    public void synchronizeOfflineChanges() {
        ArrayList<Room> offlineChanges = offlineSyncService.getAllOfflineChanges();

        for (Room room : offlineChanges) {
            saveOfflineChange(room);
        }
    }
}
