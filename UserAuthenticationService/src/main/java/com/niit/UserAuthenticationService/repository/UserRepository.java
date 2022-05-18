package com.niit.UserAuthenticationService.repository;

import com.niit.UserAuthenticationService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    public User findByEmailAndPassword(String email, String password);
}


