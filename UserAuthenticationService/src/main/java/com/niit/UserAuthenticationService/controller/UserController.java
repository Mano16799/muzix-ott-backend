package com.niit.UserAuthenticationService.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.UserAuthenticationService.exception.InvalidCredentialsException;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.model.ImageModel;
import com.niit.UserAuthenticationService.model.User;
import com.niit.UserAuthenticationService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@RestController
@RequestMapping("api/v1")
public class UserController {

    private ResponseEntity responseEntity;
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @HystrixCommand(fallbackMethod = "fallbackGetAllUsers")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("/user-list")
    public ResponseEntity<?> getAllUsers() {
        //  Thread.sleep(500);
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }

    @HystrixCommand(fallbackMethod = "fallbackLogin")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("/login")
    public Object verifyUser(@RequestBody User user) {
        Object obj = null;
        try {
            obj = userService.login(user);
        } catch (UserNotFoundException e) {
            obj = null;
        } catch (InvalidCredentialsException e) {
            obj = null;
        }
        return obj;
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdate")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PutMapping("/update-user")
    public Object updateUser(@RequestBody User user) {
        Object obj = null;
        try {
            userService.deleteUser(user.getEmail());
            obj = userService.saveUser(user);
        } catch (Exception e) {
            obj = new ResponseEntity<>("Error. Try after sometime", HttpStatus.CONFLICT);
        }
        return obj;
    }

    @HystrixCommand(fallbackMethod = "fallbackRegister")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("/register")
    public Object saveUser(@RequestBody User user) {
        Object obj = null;
        try {
            obj = userService.saveUser(user);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @HystrixCommand(fallbackMethod = "fallbackUpload")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PutMapping("/upload")
    public ResponseEntity.BodyBuilder updateUserImage(@RequestPart("imageFile") MultipartFile file) throws IOException, UserNotFoundException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        userService.deleteImage(img.getEmail());
        userService.saveImage(img);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "fallbackUpload")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("/upload")
    public Object uploadImage(@RequestPart("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        userService.saveImage(img);
        return new ResponseEntity("Uploaded successfully",HttpStatus.OK);
    }


    @HystrixCommand(fallbackMethod = "fallbackDelete")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("user/{email}")
    public Object deleteUser(@PathVariable("email") String email) {
        Object obj = null;
        try {
            obj = userService.deleteUser(email);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @GetMapping(path = {"get/{email}"})
    public ImageModel getImage(@PathVariable("email") String email) {
        final Optional<ImageModel> retrievedImage = userService.findImageByEmail(email);
        ImageModel img = new ImageModel(retrievedImage.get().getEmail(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        System.out.println(img);
        return img;
    }

    @HystrixCommand(fallbackMethod = "fallbackDelete")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("user-image/{email}")
    public Object deleteImage(@PathVariable("email") String email) {
        try {
            userService.deleteImage(email);
            responseEntity = new ResponseEntity("Image Deleted Successfully !!!", HttpStatus.OK);
        } catch (Exception exception) {
            responseEntity = new ResponseEntity("Error !!! Try after sometime.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    public ResponseEntity<?> fallbackGetAllUsers() {
        String msg = "User Authentication service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackLogin(User user) {
        String msg = "User Authentication service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackRegister(User user) {
        String msg = "User Authentication service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackDelete(String email) {
        String msg = "User Authentication service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity.BodyBuilder fallbackUpload(MultipartFile file) {
        String msg = "User Authentication service is down!!! Try Again later";
        //return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackUpdate(User user) {
        String msg = "User Authentication service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }


//    public ResponseEntity<?> fallbackCompress(byte[] data) {
//        String msg = "User Authentication service is down!!! Try Again later";
//        //return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
//    }

    //    @HystrixCommand(fallbackMethod = "fallbackUpload")
//    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
//    @PostMapping("/upload")
//    public Object uploadImage(@RequestPart("imageFile") MultipartFile file) throws IOException {
//        System.out.println("Original Image Byte Size - " + file.getBytes().length);
//        Object obj=null;
//         obj= new ImageModel(file.getOriginalFilename(), file.getContentType(),
//                compressBytes(file.getBytes()));
//       userService.saveImage( (ImageModel)obj);
//        return obj;
//    }
}
