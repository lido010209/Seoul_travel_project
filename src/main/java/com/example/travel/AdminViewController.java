package com.example.travel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminViewController {
    @GetMapping("message")
    public String manageUser(){
        return "message/admin/listUser";
    }
    @GetMapping("message/{receiverId}")
    public String message(){
        return "message/admin/mesAdmin";
    }
    @GetMapping("booking")
    public String booking(){
        return "booking/admin/allBooking";
    }
}
