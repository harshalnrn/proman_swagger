package com.upgrad.proman.service.dao;

import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository

public class LoginDao {

  @PersistenceContext EntityManager entityManager;

  public UserEntity validateUsername(String username) {

    try {
      TypedQuery<UserEntity> query =
          entityManager.createNamedQuery("userByEmail", UserEntity.class);
      query.setParameter("email", username);
      UserEntity entity = query.getSingleResult();
      return entity;
    } catch (NoResultException e) {
      return null;
    }
  }


  public UserAuthTokenEntity saveLoginUserToken(UserAuthTokenEntity userAuthTokenEntity) throws AuthenticationFailedException {

 //   try {
      entityManager.persist(userAuthTokenEntity);
      return userAuthTokenEntity;
  //  }

    //Application exception overridden by commit exception
   /* catch (Exception e) {
       throw new AuthenticationFailedException("ATH-003","Error saving user token");
    }*/

  }

  public UserEntity validateEncryptedPassword(String username, String password) {
    try {
      TypedQuery<UserEntity> query =
          entityManager
              .createNamedQuery("userByPasword", UserEntity.class)
              .setParameter("username", username);
      query.setParameter("encrypPassword", password);
      return query.getSingleResult();
    } catch (NoResultException e) {

      return null;
    }
  }

  public UserEntity updateUserEntity(UserEntity userEntity) throws  AuthenticationFailedException {
    try {
      return entityManager.merge(userEntity); // checks if pk is existing and updates it
    }

    catch (Exception e) {
       throw new AuthenticationFailedException("ATH-004","Errror updating last login");
    }

  }
}