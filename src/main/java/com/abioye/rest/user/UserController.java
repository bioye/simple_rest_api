package com.abioye.rest.user;

import java.time.LocalDate;
import java.util.Optional;

import javax.mail.MessagingException;

import com.abioye.rest.EmailClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController{

@GetMapping("/api/users/{id}")
User getOne(@PathVariable Long id){
  return repository.findById(id)
    .orElseThrow(() -> new UserNotFoundException(id));
}

  @GetMapping("/api/users")
  Iterable<User> getAll(){
    return repository.findAll();
  }

  @GetMapping("/api/users/{id}/verify")
  void verifyUser(@PathVariable Long id) throws MessagingException{
    
    Optional<User> user = repository.findById(id);
    if (user.isPresent()){
      User existsUser = user.get();
      existsUser.verify();
      repository.save(existsUser);
      EmailClient.sendVerifiedEmail(emailSender, existsUser.getEmail());
    }
    else{
      throw new UserNotFoundException(id);
    }
  }

  @PatchMapping("/api/users/{id}")
  void deleteUser(@PathVariable Long id) throws MessagingException{
    
    Optional<User> user = repository.findById(id);
    if (user.isPresent()){
      User existsUser = user.get();
      existsUser.setStatus(User.Status.DEACTIVATED);
      existsUser.setDateDeactivated(LocalDate.now());
      repository.save(existsUser);
      EmailClient.sendDeactivatedEmail(emailSender, existsUser.getEmail());
    }
    else{
      throw new UserNotFoundException(id);
    }
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
        repository.save(user);
        return user;
      })
    .orElseGet(() -> {
      newUser.setId(id);
      return repository.save(newUser);
    });
  }

  @PostMapping("/api/user")
  User registerUser(@RequestBody User newUser) throws MessagingException{
    String encryptedPassword = new BCryptPasswordEncoder().encode(new String(newUser.getPassword()));
    String email = newUser.getEmail();
    User preparedUser = new User(
      newUser.getRole(), 
      newUser.getTitle(), 
      newUser.getFirstName(), 
      newUser.getLastName(),
      email,
      newUser.getMobile(),
      encryptedPassword.toCharArray());

      preparedUser = repository.save(preparedUser);
    EmailClient.sendRegisteredEmail(emailSender, preparedUser.getId(), email);
    return preparedUser;
  }

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @Autowired
  private JavaMailSender emailSender;

  private final UserRepository repository;
}