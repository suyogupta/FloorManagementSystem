package com.floorplanmanagment.Project;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfflineSyncService {

    @Autowired
    private OfflineChangeRepository offlineChangeRepository;


    public void saveOfflineChange(Room room) {
        offlineChangeRepository.saveInfo(room);
    }

    public ArrayList<Room> getAllOfflineChanges()
    {
        ArrayList<Room> res = offlineChangeRepository.findAll();
        offlineChangeRepository.deleteAll();
        return res;
    }

}
