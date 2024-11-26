package com.example.travel;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("travel")
public class WebViewController {
    @GetMapping("home")
    public String home(){
        return "home";
    }
    @GetMapping("map")
    public String view(){
        return "direction/map";
    }
    @GetMapping("message")
    public String message(){
        return "message/user/mesUser";
    }
    @GetMapping("login")
    public String login(){
        return "user/login";
    }
    @GetMapping("signup")
    public String register(){
        return "user/create";
    }
    @GetMapping("profile")
    public String profile(){
        return "user/profile";
    }
    @GetMapping("/tour")
    public String tourist(){
        return "tourist/allTourist";
    }
    @GetMapping("/package/{packageId}")
    public String tourPackage(){
        return "tourPackage/package";
    }
    @GetMapping("booking")
    public String booking(){
        return "booking/user/booking";
    }
    @GetMapping("my-booking")
    public String allBooking(){
        return "booking/user/allBooking";
    }

    @GetMapping("payment/{bookId}")
    public String index() {
        return "payment/checkout";
    }

    @GetMapping("payment/success/{bookId}")
    public String successPayment(){return "payment/success";}

    @GetMapping("payment/fail/{bookId}")
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "payment/fail";
    }
    @GetMapping("post-create")
    public String postCreate1(Long postId) {return "post/post-create";}
    @GetMapping("/post/{postId}")
    public String readOnePost() {return "post/post";}
    @GetMapping("/post")
    public String readAllPost() { return "post/postingList";}

}
