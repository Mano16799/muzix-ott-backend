package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.model.User;
import com.niit.UserAuthenticationService.repository.UserRepository;
import com.niit.UserAuthenticationService.services.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    //in this object inject the dummy object as external dependency
    @InjectMocks
    private UserServiceImpl userService;
    private User user1,user2;
    List<User> userList;



    @BeforeEach
    public void setUp(){

        user1=new User("purna447@gmail.com","purna","chander","purna@447","7337282026");
        user2=new User("kalyani@gmail.com","kalyani","venkata","kalyani@123","8888888888");
        userList = Arrays.asList(user1,user2);
    }

    @AfterEach
    public void tearDown()
    {
       user1=null;
       user2=null;

    }

    @Test
    public void givenUserToSaveReturnSavedUserSuccess() throws UserAlreadyExistsException {

        when(userRepository.findById(user1.getEmail())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any())).thenReturn(user1);

        assertEquals(user1,userService.saveUser(user1));

        verify(userRepository,times(1)).save(any());
        verify(userRepository,times(1)).findById(any());

    }

    @Test
    public void givenUserToSaveReturnUserFailure(){

        when(userRepository.findById(user1.getEmail())).thenReturn(Optional.ofNullable(user1));

        assertThrows(UserAlreadyExistsException.class,()->userService.saveUser(user1));

        verify(userRepository,times(0)).save(any());
        verify(userRepository,times(1)).findById(any());
    }

    @Test
    public void givenUserToDeleteShouldDeleteSuccess() throws UserAlreadyExistsException, UserNotFoundException {
        when(userRepository.findById(user1.getEmail())).thenReturn(Optional.ofNullable(user1));
        boolean flag = userService.deleteUser(user1.getEmail());
        assertEquals(true,flag);

        verify(userRepository,times(1)).deleteById(any());
        verify(userRepository,times(1)).findById(any());
    }
}
