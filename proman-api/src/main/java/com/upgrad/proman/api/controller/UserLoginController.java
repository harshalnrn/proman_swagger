package com.upgrad.proman.api.controller;

import com.upgrad.proman.api.model.AuthorizedUserResponse;
import com.upgrad.proman.service.business.UserLoginService;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;
    @RequestMapping( path="auth/login",method= RequestMethod.POST,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
//encoded format: Basic YWJoaTpwYXNzd29yZA==’ is entered in the authorization header.
       // <Base 64 Decode username, password.Send both to Service>
        byte[] decode=Base64.getDecoder().decode(authorization.split("Basic")[1]);
String decodedText=new String(decode);//convert byte[] to string
String[] text=decodedText.split(":");
//‘abhi:password’ is the base64 decoded format
UserAuthTokenEntity userAuthTokenEntity=userLoginService.authenticate(text[0], text[1]);

AuthorizedUserResponse authorizedUserResponse=new AuthorizedUserResponse();
authorizedUserResponse.setFirstName(userAuthTokenEntity.getUser().getFirstName());
authorizedUserResponse.setEmailAddress(userAuthTokenEntity.getUser().getEmail());
authorizedUserResponse.setLastLoginTime(userAuthTokenEntity.getUser().getLastLoginAt());
authorizedUserResponse.setId(UUID.fromString(userAuthTokenEntity.getUser().getUuid()));

//passing the token with http header
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("access-token",userAuthTokenEntity.getAccessToken());
return new ResponseEntity<AuthorizedUserResponse>(authorizedUserResponse, httpHeaders,HttpStatus.OK);   //responseEntity objects has the response body, http header and http status
    }
}
