package com.course.marketplace.controller;

import com.course.marketplace.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/users")
    public boolean checkEmailExists(@RequestParam(name="email",defaultValue = "")String email)
    {
        return userDetailsService.getUserByEmail(email)!=null;
    }
    @GetMapping("/user")
    public boolean checkUserNameExists(@RequestParam(name="userName",defaultValue="")String userName)
    {
        return userDetailsService.getUserByUserName(userName)!=null;
    }
}
