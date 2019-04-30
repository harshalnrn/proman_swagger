package com.upgrad.proman.service.business;


import com.upgrad.proman.model.SignupUserRequest;
import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class SignupBusinessService {


    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;
    @Autowired
    UserDao userDao;
    @Autowired
    AdminUserBusinessService adminUserBusinessService;
    /*@Autowired
    UserEntity userEntity;*/
    //every business service should be running with transactional
    @Transactional(propagation = Propagation.REQUIRED)

    public UserEntity signup(SignupUserRequest signupUserRequest) {
//populate entiry from model/request bean

       // String[] encrypedData=passwordCryptographyProvider.encrypt(signupUserRequest.getPassword()); //encryped password, salt generated via hashing
        UserEntity userEntity=new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
       // userEntity.setPassword(encrypedData[1]);
        userEntity.setMobilePhone(signupUserRequest.getMobileNumber());
      //  userEntity.setSalt(encrypedData[0]);
        userEntity.setStatus(4);
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("signUpController");
return adminUserBusinessService.createUser(userEntity);
        //return userDao.createUser(userEntity);
    }
}

//the common business logic for both signup and createUser can be centralised at one side.