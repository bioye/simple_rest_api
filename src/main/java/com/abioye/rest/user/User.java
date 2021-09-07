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