package com.upgrad.proman.service.business;

import com.upgrad.proman.service.dao.AdminUserDao;
import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdminUserBusinessService {

    @Autowired
AdminUserDao adminUserDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;
    @Autowired
    private UserDao userDao;
    //every business service should be running with transactional
   @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity getUser(final String userUuid) throws ResourceNotFoundException{

UserEntity userEntity=adminUserDao.getUser(userUuid);
if (userEntity == null){
  throw new ResourceNotFoundException("USR-001","user not found");
        }

        return userEntity;
    }

    //method to be used for signup too
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity createUser(final UserEntity userEntity){
        userEntity.setUuid(UUID.randomUUID().toString());
        String password = userEntity.getPassword();
        if(password == null){
            userEntity.setPassword("proman@123");
        }
        String[] encryptedText = cryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);
        return userDao.createUser(userEntity);   //same CRUD operation as that of signup

    }
}


