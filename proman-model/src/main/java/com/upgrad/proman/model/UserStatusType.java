package com.upgrad.proman.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status of the User
 */
public enum UserStatusType {
  
  REGISTERED("REGISTERED"),
  
  ACTIVE("ACTIVE"),
  
  INACTIVE("INACTIVE"),
  
  LOCKED("LOCKED"),
  
  DELETED("DELETED");

  private String value;

  UserStatusType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static UserStatusType fromValue(String text) {
    for (UserStatusType b : UserStatusType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

