package com.example.PhotoApp.Controller;


import com.example.PhotoApp.DTO.UserDTO;
import com.example.PhotoApp.Model.UserDetails;
import com.example.PhotoApp.ResponseModel.UserResponse;
import com.example.PhotoApp.Service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( path = "users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  public String  getUser(){
    return "get user was called";
  }

  @PostMapping
  public UserResponse postUser(@RequestBody UserDetails userDetails ){

    UserResponse returnValue = new UserResponse();

    UserDTO userDto = new UserDTO();
    BeanUtils.copyProperties(userDetails, userDto);

    UserDTO createdUser = userService.createUser(userDto);
    BeanUtils.copyProperties(createdUser, returnValue);

    return returnValue;
  }

  @PutMapping
  public String  UpdateUser(){
    return "update user was called";
  }

  @DeleteMapping
  public String  deleteUser(){
    return "delete user was called";
  }
}
