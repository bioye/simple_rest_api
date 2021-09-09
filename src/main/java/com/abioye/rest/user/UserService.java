package com.abioye.rest.user;

import java.util.Optional;

import javax.mail.MessagingException;

import com.abioye.rest.MailingHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public User getOne(Long id) {
    return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  public Iterable<User> getAll() {
    return repository.findAll();
  }

  public void verifyUser(Long id) {
    Optional<User> user = repository.findById(id);
    if (user.isPresent()) {
      User existsUser = user.get();
      existsUser.verify();
      saveUser(existsUser);
      MailingHelper.sendMail(existsUser.getEmail(), "verified");
    } else {
      throw new UserNotFoundException(id);
    }
  }

  public void deactivateUser(Long id) {
    Optional<User> user = repository.findById(id);
    if (user.isPresent()) {
      User existsUser = user.get();
      existsUser.deactivate();
      repository.save(existsUser);
      MailingHelper.sendMail(existsUser.getEmail(), "deactivated");
    }
    else{
      throw new UserNotFoundException(id);
    }
  }

  public User updateUser(UserDTO newUserDto, Long id) {
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
      return saveUser(freshUser);
    });
  }

  public User registerUser(UserDTO newUserDto) throws MessagingException {
    final String encryptedPassword = new BCryptPasswordEncoder().encode(new String(newUserDto.getPassword()));
    final String email = newUserDto.getEmail();
    User preparedUser = new User(newUserDto.getRole(), newUserDto.getTitle(), newUserDto.getFirstName(),
        newUserDto.getLastName(), email, newUserDto.getMobile(), encryptedPassword.toCharArray());

    preparedUser = saveUser(preparedUser);
    MailingHelper.sendRegisteredEmail(preparedUser.getId(), email);
    return preparedUser;
  }

  public User saveUser(User user) {
    return repository.save(user);
  }

}
