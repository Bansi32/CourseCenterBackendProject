package com.course.marketplace.service;

import com.course.marketplace.dao.RoleDao;
import com.course.marketplace.dao.UserDetailsDao;
import com.course.marketplace.entity.Role;
import com.course.marketplace.entity.UserDetails;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class UserImpl implements UserDetailsService{
   @Autowired
    private UserDetailsDao userDetailsDao;
   @Autowired
   private RoleDao roleDao;
  // @Autowired
  // private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails getUserByEmail(String email) {
        return userDetailsDao.findByEmail(email);
    }

    @Override
    public UserDetails createUser(String userName, String email, String password) {
        //String encodedPassword=passwordEncoder.encode(password);
        UserDetails user=new UserDetails(userName,email,password);
       //log.info(encodedPassword);
        return userDetailsDao.save(user);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        UserDetails userDetails=getUserByEmail(email);
        Role role=roleDao.findByName(roleName);
        userDetails.assignRoleToUser(role);
    }

    @Override
    public UserDetails getUserByUserName(String userName) {
        return userDetailsDao.findByUserName(userName);
    }
}
