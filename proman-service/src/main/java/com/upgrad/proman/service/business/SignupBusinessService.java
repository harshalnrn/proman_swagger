package com.upgrad.proman.service.business;


import com.upgrad.proman.model.SignupUserRequest;
import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class SignupBusinessService {


    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;
    @Autowired
    UserDao userDao;
    /*@Autowired
    UserEntity userEntity;*/

    public UserEntity signup(SignupUserRequest signupUserRequest) {
//populate entiry from model/request bean

        String[] encrypedData=passwordCryptographyProvider.encrypt(signupUserRequest.getPassword()); //encryped password, salt generated via hashing
        UserEntity userEntity=new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        userEntity.setPassword(encrypedData[1]);
        userEntity.setMobilePhone(signupUserRequest.getMobileNumber());
        userEntity.setSalt(encrypedData[0]);
        userEntity.setStatus(4);
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("api-backend");
        return userDao.createUser(userEntity);
    }
}
