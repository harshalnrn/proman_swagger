package com.upgrad.proman.service.business;

import com.upgrad.proman.service.dao.AdminUserDao;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserBusinessService {

    @Autowired
AdminUserDao adminUserDao;
    public UserEntity getUser(final String userUuid) throws ResourceNotFoundException{

UserEntity userEntity=adminUserDao.getUser(userUuid);
if (userEntity == null){
  throw new ResourceNotFoundException("USR-001","user not found");
        }

        return userEntity;
    }
}
