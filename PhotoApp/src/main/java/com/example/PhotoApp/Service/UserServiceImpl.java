package com.example.PhotoApp.Service;

import com.example.PhotoApp.DTO.UserDTO;
import com.example.PhotoApp.DTO.Utils;
import com.example.PhotoApp.Entity.UserEntity;
import com.example.PhotoApp.Repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  Utils utils;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDTO createUser(UserDTO user) {

    if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exist");

    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(user, userEntity);

    String publicUserId = utils.generateUserId(30);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    userEntity.setUserId(publicUserId);

    UserEntity storedUserDetails = userRepository.save(userEntity);

    UserDTO returnValue = new UserDTO();
    BeanUtils.copyProperties(storedUserDetails,returnValue);

    return returnValue;
  }

  @Override
  public UserDTO getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if(userEntity ==  null) throw new UsernameNotFoundException(email);
    UserDTO returnValue = new UserDTO();
    BeanUtils.copyProperties(userEntity,returnValue);
    return returnValue;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);

    if(userEntity ==  null) throw new UsernameNotFoundException(email);
    return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
  }
}
