package com.abioye.rest.user;

import java.time.LocalDate;

public class UserDTO {

  private Role role;
  private Status status;
  private String title;
  private String firstName;
  private String lastName;
  private String email;
  private String mobile;
  private boolean verified;
  private LocalDate dateRegistered;
  private LocalDate dateVerified;
  private LocalDate dateDeactivated;
  private char[] password;

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public LocalDate getDateRegistered() {
    return dateRegistered;
  }

  public void setDateRegistered(LocalDate dateRegistered) {
    this.dateRegistered = dateRegistered;
  }

  public LocalDate getDateVerified() {
    return dateVerified;
  }

  public void setDateVerified(LocalDate dateVerified) {
    this.dateVerified = dateVerified;
  }

  public LocalDate getDateDeactivated() {
    return dateDeactivated;
  }

  public void setDateDeactivated(LocalDate dateDeactivated) {
    this.dateDeactivated = dateDeactivated;
  }

  public char[] getPassword() {
    return password;
  }

  public void setPassword(char[] password) {
    this.password = password;
  }

}