package com.abioye.rest.user;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController{
  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/api/user")
  User registerUser(@RequestBody User newUser){
    return repository.save(newUser);
  }

  @GetMapping("/api/users")
  List<User> getAll(){
    return repository.findAll();
  }

  @PutMapping("api/user/{id}")
  User updateUser(@RequestBody User newUser, @PathVariable Long id){
    return repository.findById(id)
      .map(user -> {
        user.setTitle(newUser.getTitle());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setMobile(newUser.getMobile());
        user.setRole(newUser.getRole());
        user.setStatus(newUser.getStatus());
        user.setPassword(newUser.getPassword());//
        user.setVerified(newUser.isVerified());
        user.setDateRegistered(newUser.getDateRegistered());
        user.setDateVerified(newUser.getDateVerified());
        user.setDateDeactivated(newUser.getDateDeactivated());
        return user;
      })
    .orElseGet(() -> {
      newUser.setId(id);
      return repository.save(newUser);
    });
  }

  @PatchMapping("/api/users/{id}")
  void deleteUser(@PathVariable Long id){
    
    Optional<User> user = repository.findById(id);
    if (user.isPresent())
      user.get().setStatus(User.Status.DEACTIVATED);
    else  
      throw new UserNotFoundException(id);
  }

}