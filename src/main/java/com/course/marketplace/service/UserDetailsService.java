package com.course.marketplace.service;

import com.course.marketplace.entity.UserDetails;

public interface UserDetailsService {
    UserDetails getUserByEmail(String email);
    UserDetails createUser(String userName,String email,String password);
    void assignRoleToUser(String email,String roleName);
    UserDetails getUserByUserName(String userName);
}
