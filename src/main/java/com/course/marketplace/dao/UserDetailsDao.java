package com.course.marketplace.dao;

import com.course.marketplace.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsDao extends JpaRepository<UserDetails,Long> {
    UserDetails findByEmail(String Email);

    UserDetails findByUserName(String userName);
}
