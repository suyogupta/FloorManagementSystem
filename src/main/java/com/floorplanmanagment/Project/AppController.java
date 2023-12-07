package com.floorplanmanagment.Project;



import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.service.annotation.GetExchange;

import com.floorplanmanagment.Project.com.floorplanmanagment.Project.Booking;

@Controller
public class AppController {
    
    @Autowired
    private FloorPlanService floorPlanService;

    @RequestMapping("/")
    public String loginPage(Model model)
    {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("/home")
    public String firstHandler(Model model){
        
        return "home";
    }
    @RequestMapping("/room")
    public String roomPage(Model model){
        model.addAttribute("booking", new Booking());
        return "room";
    }
    @RequestMapping("/save")
    public String savePage(Model model){
        model.addAttribute("room", new Room());
        return "save";
    }

    @GetMapping("/getAll")
    @ResponseBody
    public ArrayList<Room> getAll()
    {
        return floorPlanService.getAll();
    }

    @GetMapping("/getById")
    @ResponseBody
    public String getById(int id)
    {
        return floorPlanService.getRoomByRoomId(id).toString();
    }

    @PostMapping("/saveRoom")
    public String saveRoomInfo(@ModelAttribute("room") Room room,BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            return "save";
        }
        String res = floorPlanService.savefloorPlan(room);
        if(res.equals("SUCCESS"))
        {
            model.addAttribute("message", "Information Updated Successfully");
        }
        else{
            model.addAttribute("message", res);
        }

        return "message";
    }

    @PostMapping("/book")
    public String bookRoom(@ModelAttribute("booking") Booking booking, BindingResult bindingResult, Model model)
    {
        int roomId = floorPlanService.getRoomAllocation(booking);
        if(roomId==-1)
        {
            model.addAttribute("message", "No rooms available");
        }
        else
        {
            model.addAttribute("message", "Room No "+roomId+" Booked");
        }
        return "message";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model)
    {
        String res = floorPlanService.validateUser(user.getId(), user.getPassword());
        if(res==Constants.INVALID_USER)
        {
            model.addAttribute("message", "User does not exist");
            return "message2";
        }
        else if(res==Constants.INVALID_PASSWORD)
        {
            model.addAttribute("message", "Passowrd Entered is incorrect for the user");
            return "message2";
        }
        else
        {
            model.addAttribute("name", res);
            return "home";
        }

    }

}


