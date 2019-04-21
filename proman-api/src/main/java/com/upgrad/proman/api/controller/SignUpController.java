package com.upgrad.proman.api.controller;

import com.upgrad.proman.model.SignupUserRequest;
import com.upgrad.proman.model.SignupUserResponse;
import com.upgrad.proman.service.business.SignupBusinessService;
import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignUpController {

    @Autowired                   //note classes of other module wont be accessable here, unless you add it as dependency in pom
            SignupBusinessService signupBusinessService;


    @RequestMapping(value="signup",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest) {
        final UserEntity userEntity = signupBusinessService.signup(signupUserRequest);

        SignupUserResponse signupUserResponse = new SignupUserResponse();
        signupUserResponse.id(userEntity.getUuid()).setStatus("REGISTERED");

//responseEntity is of generic type with type being that of response model
        return new ResponseEntity<SignupUserResponse>(signupUserResponse, HttpStatus.CREATED);  //here we populate response entity using response model
        //note the signupUserResponse is the response model in line with the contract, and HttpStatus belongs to Http packages
    }
}
