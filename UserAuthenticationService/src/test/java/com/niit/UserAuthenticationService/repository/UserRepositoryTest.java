package com.niit.UserAuthenticationService.repository;

import com.niit.UserAuthenticationService.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringJUnit4ClassRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
////@TestPropertySource("src/test/application.properties")
//public class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//    private User user;
//
//
//
//
//    @BeforeEach
//    public void setUp(){
//        System.out.println("Test case running");
//        user = new User("kamidi.purnachanderreddy@gmail.com", "purna", "chander", "purna@123", "8501885152");
//    }
//
//    @AfterEach
//    public void tearDown(){
//        user=null;
//        //removing all the documents of the collection as they are fake information.
//        userRepository.deleteAll();
//        System.out.println("test case stopping");
//    }
////    @Test
////    public void userShouldNotBeNull()
////    {
////
////    }
//
//    @Test
//    public void givenUserToDeleteShouldDeleteUser(){
//        userRepository.save(user);
//        User user1 = userRepository.findById(user.getEmail()).get();
//
//        userRepository.delete(user1);
//        assertEquals(Optional.empty(),userRepository.findById(user1.getEmail()));
//
//    }
//
//}


//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@DataJpaTest
//@Testcontainers
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserRepositoryTest {
//    @Container
//    static PostgreSQLContainer database = new PostgreSQLContainer("postgres:12")
//            .withDatabaseName("springboot")
//            .withPassword("springboot")
//            .withUsername("springboot");
//    @DynamicPropertySource
//    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
//        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
//        propertyRegistry.add("spring.datasource.password", database::getPassword);
//        propertyRegistry.add("spring.datasource.username", database::getUsername);
//    }
//    @Autowired
//    private OrderRepository orderRepository;
//}

