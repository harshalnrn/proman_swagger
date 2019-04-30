package com.upgrad.proman.service.business;

import com.upgrad.proman.service.common.JwtTokenProvider;
import com.upgrad.proman.service.dao.LoginDao;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
//if service not transactional, then this error received:org.hibernate.TransactionException: Transaction was marked for rollback only; cannot commit
@Service
public class UserLoginService {
  @Autowired LoginDao loginDao;
  @Autowired PasswordCryptographyProvider passwordCryptographyProvider;
  //every business service should be running with transactional
    @Transactional(propagation = Propagation.REQUIRED)

  /* 1 validate username
  2 valiate username
  3 generate JWT token for user and persist*/
  public UserAuthTokenEntity authenticate(final String username, String password)
      throws AuthenticationFailedException {

    UserEntity userEntity = loginDao.validateUsername(username);
    if (userEntity == null) {
      throw new AuthenticationFailedException("ATH-001", "Username authentication failed");
    }

    final String encryptedPassword =
        passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
    System.out.println(encryptedPassword);
    if (encryptedPassword.equals(userEntity.getPassword())) {
      // Now we do BEARER AUTHENTICATION.
        //You need to generate JWT token in this case,persist in database and return it,
      // to the user for authorizing any further requests by this particular user.
      JwtTokenProvider jwtTokenProvider =
          new JwtTokenProvider(encryptedPassword); // secret key passed is the encrypted password.

      UserAuthTokenEntity userAuthTokenEntity = new UserAuthTokenEntity();
      userAuthTokenEntity.setUser(userEntity);
      // java 8 Time class
      final ZonedDateTime now = ZonedDateTime.now(); // current time
      final ZonedDateTime expires = now.plusHours(8);

      userAuthTokenEntity.setAccessToken(
          jwtTokenProvider.generateToken(userEntity.getUuid(), now, expires));
      userAuthTokenEntity.setLoginAt(now);
      userAuthTokenEntity.setLogoutAt(expires);
      //check if 3 fields are correct wrt data. Not sure
      userAuthTokenEntity.setCreatedBy(username);
      userAuthTokenEntity.setCreatedAt(now);
      userAuthTokenEntity.setExpiresAt(expires);
      loginDao.saveLoginUserToken(userAuthTokenEntity);

      userEntity.setLastLoginAt(now);
      loginDao.updateUserEntity(userEntity);
      return userAuthTokenEntity;
    } else {
      throw new AuthenticationFailedException("ATH-002", "Password authentication failed");
    }
  }
}
