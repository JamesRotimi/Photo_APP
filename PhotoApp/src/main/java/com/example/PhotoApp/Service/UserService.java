package com.example.PhotoApp.Service;

import com.example.PhotoApp.DTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserDTO createUser(UserDTO user);
}
