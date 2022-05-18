package com.niit.UserAuthenticationService.repository;


import com.niit.UserAuthenticationService.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByEmail(String email);
}