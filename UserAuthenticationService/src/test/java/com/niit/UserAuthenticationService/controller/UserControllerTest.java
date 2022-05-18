package com.niit.UserAuthenticationService.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.niit.UserAuthenticationService.model.User;
import com.niit.UserAuthenticationService.repository.UserRepository;
import com.niit.UserAuthenticationService.services.UserService;
import com.niit.UserAuthenticationService.services.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    private User user1,user2;
    List<User> UserList;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        user1 = new User("kamidi.purnachanderreddy@gmail.com", "purna", "chander", "purna@123", "8501885152");
        user2=new User("mano@gmail.com","mano","balasubrmaniam","mano@123","9999999999");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public void tearDown() {
        user1=null;
        user2 = null;
    }

    @Test
    public void givenUserToSaveReturnSaveUserSuccess() throws Exception {
        when(userService.saveUser(any())).thenReturn(user1);

        mockMvc.perform(post("/api/v1/userService/register")//making dummy http post request
                        .contentType(MediaType.APPLICATION_JSON)//setting the content type of the post request
                        .content(jsonToString(user1)))//firstly, java object will be converted to json string then will  be passed with the mock http request.
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(any());


    }

    @Test
    public void givenToSaveReturnSaveUserFailure() throws Exception {
        when(userService.saveUser(any())).thenThrow(UserAlreadyExistsException.class);

        mockMvc.perform(post("/api/v1/userService/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().is5xxServerError()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(any());

    }

    @Test
    public void givenUserCodeDeleteUser() throws Exception {
        when(userService.deleteUser(any())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/userService/user/mano@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).deleteUser(any());

    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            System.out.println("Json Content that has been posted:\n"+jsonContent);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }

    }
