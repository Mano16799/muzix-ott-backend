package com.niit.UserAuthenticationService.services;

import com.niit.UserAuthenticationService.exception.InvalidCredentialsException;
import com.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.model.ImageModel;
import com.niit.UserAuthenticationService.model.User;
import com.niit.UserAuthenticationService.repository.ImageRepository;
import com.niit.UserAuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ImageRepository imageRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException();
        return userRepository.save(user);
    }


    @Override
    public User findByUsernameAndPassword(String email, String password) throws UserNotFoundException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(String email) throws UserNotFoundException {
        boolean flag = false;
        if (userRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            userRepository.deleteById(email);
            flag = true;
        }
        return flag;
    }

    @Override
    public User login(User user) throws UserNotFoundException, InvalidCredentialsException {
        if(userRepository.findById(user.getEmail()).isEmpty()){
            throw new UserNotFoundException();
        }
        Optional<User> foundUser = userRepository.findById(user.getEmail());
        if(!foundUser.get().getPassword().equalsIgnoreCase(user.getPassword())){
            throw new InvalidCredentialsException();
        }
        return foundUser.get();
    }


    @Override
    public ImageModel saveImage(ImageModel imageModel) {
        if(imageRepository.findByEmail(imageModel.getEmail()).isPresent())
        {
            imageRepository.delete(imageRepository.findByEmail(imageModel.getEmail()).get());
        }
        return imageRepository.save(imageModel);
    }


    @Override
    public Optional<ImageModel> findImageByEmail(String email) {
        return imageRepository.findByEmail(email);
    }

    @Override
    public boolean deleteImage(String email) throws UserNotFoundException {
        boolean flag = false;
        if (imageRepository.findByEmail(email).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            imageRepository.delete(imageRepository.findByEmail(email).get());
            flag = true;
        }
        return flag;
    }

}
