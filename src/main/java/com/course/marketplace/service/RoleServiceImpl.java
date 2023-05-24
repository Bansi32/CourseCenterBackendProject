package com.course.marketplace.service;

import com.course.marketplace.dao.RoleDao;
import com.course.marketplace.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;
    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }
}
