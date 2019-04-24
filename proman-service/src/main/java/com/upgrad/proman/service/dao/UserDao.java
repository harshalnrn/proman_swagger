package com.upgrad.proman.service.dao;

import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext    //(note why this annotation and not autowired). This ensures that this is managed by spring container and not application
    private EntityManager entityManager;               //provided by JPA //how do API objects get instantiated to be autowired
    public UserEntity createUser(UserEntity userEntity){

        entityManager.persist(userEntity); //persisting transient entity
        return userEntity;  //returning persisted entity


    }

}
