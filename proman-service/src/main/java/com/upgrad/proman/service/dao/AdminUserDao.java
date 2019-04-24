package com.upgrad.proman.service.dao;

import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class AdminUserDao {

    @PersistenceContext
    EntityManager e;           //note: @PersistenceContext manages lifecycle of all persistence unit.
                              // injects entityManager here, and gives you opportunity to specify which persistence unit you want to use

    public UserEntity getUser(final String userUuid) {
try{
        //return e.find(UserEntity.class, userUuid);     //or write JPQL programmatically or use @NamedQuery om entity class or write nativeSQL
        return e.createNamedQuery("userByUuid",UserEntity.class).setParameter("uuid",userUuid).getSingleResult();

    }
    catch (NoResultException e){
    return null;
    }

    }
}
