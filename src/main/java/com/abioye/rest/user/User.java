package com.abioye.rest.user;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class User {

  @Override
  public String toString() {
    return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", mobile=" + mobile
        + ", title=" + title + ", dateRegistered=" + dateRegistered + ", dateVerified=" + dateVerified
        + ", dateDeactivated=" + dateDeactivated + ", verified=" + verified + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    return true;
  }

  public User(Role role, String title, String firstName, String lastName, String email, String mobile,
      char[] password) {
    this.role = role;
    this.title = title;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.mobile = mobile;
    this.password = password;
    this.status = User.Status.REGISTERED;
    this.dateRegistered = LocalDate.now();
  }

  public User() {
  }

  public Role getRole() {
    return role;
  }

  public Status getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getMobile() {
    return mobile;
  }

  public char[] getPassword() {
    return password;
  }
  public LocalDate getDateRegistered() {
    return dateRegistered;
  }

  public boolean isVerified() {
    return verified;
  }

  public LocalDate getDateVerified() {
    return dateVerified;
  }

  public LocalDate getDateDeactivated() {
    return dateDeactivated;
  }

  public Long getId() {
    return id;
  }
  enum Role {
    USER, ADMIN
  }

  enum Status {
    REGISTERED, VERIFIED, DEACTIVATED
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setPassword(char[] password) {
    this.password = password;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public void setDateRegistered(LocalDate dateRegistered) {
    this.dateRegistered = dateRegistered;
  }

  public void setDateVerified(LocalDate dateVerified) {
    this.dateVerified = dateVerified;
  }

  public void setDateDeactivated(LocalDate dateDeactivated) {
    this.dateDeactivated = dateDeactivated;
  }

  public void setId(Long id) {
    this.id = id;
  }

  void verify() {
    setStatus(User.Status.VERIFIED);
    setVerified(true);
    setDateVerified(LocalDate.now());
  }

  private Role role;
  private Status status;
  private String title;
  private String firstName;
  private String lastName;
  private String email;
  private String mobile;
  private char[] password;
  private LocalDate dateRegistered;
  private boolean verified;
  private LocalDate dateVerified;
  private LocalDate dateDeactivated;
  private @Id @GeneratedValue Long id;
}