package com.abioye.rest.user;

import java.time.LocalDate;
import java.util.Optional;
import javax.mail.MessagingException;
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
import com.abioye.rest.MailingHelper;

@RestController
class UserController {

  @Autowired
  private JavaMailSender emailSender;

  private final UserRepository repository;

  @GetMapping("/api/users/{id}")
  public User getOne(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  @GetMapping("/api/users")
  public Iterable<User> getAll() {
    return repository.findAll();
  }

  @GetMapping("/api/users/{id}/verify")
  public void verifyUser(@PathVariable final Long id) {

    Optional<User> user = repository.findById(id);
    if (user.isPresent()) {
      User existsUser = user.get();
      existsUser.verify();
      repository.save(existsUser);
      MailingHelper.sendVerifiedEmail(emailSender, existsUser.getEmail());
    } else {
      throw new UserNotFoundException(id);
    }
  }

  @PatchMapping("/api/users/{id}")
  public void deleteUser(@PathVariable Long id) {

    Optional<User> user = repository.findById(id);
    if (user.isPresent()) {
      User existsUser = user.get();
      existsUser.setStatus(Status.DEACTIVATED);
      existsUser.setDateDeactivated(LocalDate.now());
      repository.save(existsUser);
      MailingHelper.sendDeactivatedEmail(emailSender, existsUser.getEmail());
    } else {
      throw new UserNotFoundException(id);
    }
  }

  @PutMapping("api/user/{id}")
  public User updateUser(@RequestBody UserDTO newUserDto, @PathVariable Long id) {
    return repository.findById(id).map(user -> {
      user.setTitle(newUserDto.getTitle());
      user.setFirstName(newUserDto.getFirstName());
      user.setLastName(newUserDto.getLastName());
      user.setEmail(newUserDto.getEmail());
      user.setMobile(newUserDto.getMobile());
      user.setRole(newUserDto.getRole());
      repository.save(user);
      return user;
    }).orElseGet(() -> {
      User freshUser = new User();
      freshUser.setId(id);
      return repository.save(freshUser);
    });
  }

  @PostMapping("/api/user")
  public User registerUser(@RequestBody final UserDTO newUserDto) throws MessagingException {
    final String encryptedPassword = new BCryptPasswordEncoder()
      .encode(new String(newUserDto.getPassword()));
    final String email = newUserDto.getEmail();
    User preparedUser = new User(
      newUserDto.getRole(), newUserDto.getTitle(), 
      newUserDto.getFirstName(), newUserDto.getLastName(),
      email, newUserDto.getMobile(), encryptedPassword.toCharArray());

    preparedUser = repository.save(preparedUser);
    MailingHelper.sendRegisteredEmail(emailSender, preparedUser.getId(), email);
    return preparedUser;
  }

  public UserController(UserRepository repository) {
    this.repository = repository;
  }
}