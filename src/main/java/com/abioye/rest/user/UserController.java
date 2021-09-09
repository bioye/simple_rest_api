package com.abioye.rest.user;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/api/users/{id}")
  public User getOne(@PathVariable Long id) throws UserNotFoundException {
    return userService.getOne(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  @GetMapping("/api/users")
  public Iterable<User> getAll() {
    return userService.getAll();
  }

  @GetMapping("/api/users/{id}/verify")
  public void verifyUser(@PathVariable final Long id) {
    if (!userService.verifyUser(id)) {
      throw new UserNotFoundException(id);
    }
  }

  @PatchMapping("/api/users/{id}")
  public void deleteUser(@PathVariable Long id) {
    if (!userService.deactivateUser(id)) {
      throw new UserNotFoundException(id);
    }
  }

  @PutMapping("api/user/{id}")
  public User updateUser(@RequestBody UserDTO newUserDto, @PathVariable Long id) {
    return userService.updateUser(newUserDto, id);
  }

  @PostMapping("/api/user")
  public User registerUser(@RequestBody final UserDTO newUserDto) throws MessagingException {
    return userService.registerUser(newUserDto);
  }

}