package com.upgrad.proman.api.controller;

import com.upgrad.proman.model.CreateUserRequest;
import com.upgrad.proman.model.CreateUserResponse;
import com.upgrad.proman.model.UserDetailsResponse;
import com.upgrad.proman.model.UserStatusType;
import com.upgrad.proman.service.business.AdminUserBusinessService;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RequestMapping("/users")
@RestController
public class UserAdminOperationsController {

  @Autowired AdminUserBusinessService adminUserBusinessService;

  @RequestMapping(
      path = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String userUuid)
      throws ResourceNotFoundException {

    // authenticate is the user is admin, only then all them to consume the end url

    final UserEntity userEntity = adminUserBusinessService.getUser(userUuid);

    UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

    userDetailsResponse.setFirstName(userEntity.getFirstName());
    userDetailsResponse.setLastName(userEntity.getLastName());
    userDetailsResponse.setEmailAddress(userEntity.getEmail());
    userDetailsResponse.setMobileNumber(userEntity.getMobilePhone());
    userDetailsResponse.setId(userEntity.getUuid());
    userDetailsResponse.setStatus(
        UserStatusType.valueOf(
            UserStatus.getEnum(userEntity.getStatus())
                .name())); // 1 get enum mapped to integer and convert to string 2. convert string
                           // to another enum type//why 2 enum classes used in design
    return new ResponseEntity<UserDetailsResponse>(
        userDetailsResponse,
        HttpStatus.OK); // thus we send the populated response model along with httpstatus

    // so here one may think that : in case of exception anywhere, the response of controller would
    // be the custom exception object, which would get transformed into JSON output.
    // But this does not happen. BECAUSE THE PROPAGATED EXCEPTION IS LEFT UNHANDLED, LEADING TO
    // INFINITE PROPOGATION. Hence throw custom exception object and handle it via @ControllerAdvice, @ExceptionHandler
  }

  @RequestMapping(
      method = RequestMethod.POST,
      path = "/userCreation",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CreateUserResponse> createUser(
     /* @RequestBody*/ final CreateUserRequest userRequest) {    //note difference between having @RequestBody, and not having for requestmodel

UserEntity userEntity=new UserEntity();
userEntity.setFirstName(userRequest.getFirstName());
userEntity.setLastName(userRequest.getLastName());
userEntity.setEmail(userRequest.getEmailAddress());
userEntity.setMobilePhone(userRequest.getMobileNumber());
userEntity.setCreatedAt( ZonedDateTime.now());
userEntity.setCreatedBy("userAdminOperationsController");
userEntity.setStatus(UserStatus.ACTIVE.getCode());    //users created by admin shall be in ACTIVE state

    final UserEntity createdUserByAdmin=adminUserBusinessService.createUser(userEntity);
    CreateUserResponse createUserResponse=new CreateUserResponse();
createUserResponse.setId(createdUserByAdmin.getUuid());
createUserResponse.setStatus(UserStatusType.REGISTERED);

    return new ResponseEntity<CreateUserResponse>(  createUserResponse ,HttpStatus.OK);
  }
}
