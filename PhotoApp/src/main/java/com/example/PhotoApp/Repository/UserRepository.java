package com.example.PhotoApp.Repository;

import com.example.PhotoApp.Entity.UserEntity;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, ID> {

  UserEntity findByEmail(String email);
  UserEntity findByUserId(String UserId);
}
