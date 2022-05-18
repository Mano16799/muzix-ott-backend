package com.niit.proxy;

import com.niit.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient(name = "user-authentication-service", url = "localhost:8081")
public interface UserProxy {
    @PostMapping("api/v1/register")
    Object saveUser(@RequestBody User user);

    @PostMapping(value = "api/v1/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Object uploadImage(@RequestPart("imageFile") MultipartFile file) throws IOException;

    @PostMapping("api/v1/login")
    Object verifyUser(@RequestBody User user);

    @PutMapping("api/v1/update-user")
    Object updateUser(@RequestBody User user);

    @DeleteMapping("api/v1/user-image/{email}")
    Object  deleteImage(@PathVariable("email") String email);

    @DeleteMapping("api/v1/user/{email}")
    Object deleteUser(@PathVariable("email") String email);
}
