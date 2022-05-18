package com.niit.UserAuthenticationService.services;

import com.niit.UserAuthenticationService.exception.InvalidCredentialsException;
import com.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.model.ImageModel;
import com.niit.UserAuthenticationService.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User findByUsernameAndPassword(String username , String password) throws UserNotFoundException;
    List<User> getAllUsers();
    boolean deleteUser(String email) throws UserNotFoundException;
    User login(User user) throws UserNotFoundException, InvalidCredentialsException;
    ImageModel saveImage(ImageModel imageModel);
    Optional<ImageModel> findImageByEmail(String email);
    boolean deleteImage(String email) throws UserNotFoundException;

}

