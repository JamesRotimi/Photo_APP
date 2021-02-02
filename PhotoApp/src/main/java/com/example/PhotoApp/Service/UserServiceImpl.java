package com.example.PhotoApp.Service;

import com.example.PhotoApp.DTO.UserDTO;
import com.example.PhotoApp.DTO.Utils;
import com.example.PhotoApp.Entity.UserEntity;
import com.example.PhotoApp.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  Utils utils;

  @Override
  public UserDTO createUser(UserDTO user) {

    if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exist");

    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(user, userEntity);

    String publicUserId = utils.generateUserId(30);
    userEntity.setEncryptedPassword("test");
    userEntity.setUserId(publicUserId);

    UserEntity storedUserDetails = userRepository.save(userEntity);

    UserDTO returnValue = new UserDTO();
    BeanUtils.copyProperties(storedUserDetails,returnValue);

    return returnValue;
  }
}
