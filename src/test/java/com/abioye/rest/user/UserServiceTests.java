package com.abioye.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @Mock
  private UserRepository repository;
  @InjectMocks
  private UserService userService;

  @Test
  void testDeactivateUser() {
  }

  @Test
  void getAll() {
    List<User> expectedUsers = Arrays.asList(
      new User(1L, Role.USER,
      "Mr", "Abioye", "Bankole", "bioyexx@yahoo.com",
      "08127350135", new char[]{'p','a','s','s','w','o','r','d'}),
      new User(2L, Role.USER,
      "Ms", "Kiki", "Mana", "kik@yahoo.com",
      "090xx8110f3", new char[]{'p','a','s','s','w','o','r','d'})
    );
    when(repository.findAll()).thenReturn(expectedUsers);

    Iterable<User> actualUsers = userService.getAll();
    long actualUserCount = StreamSupport.stream(actualUsers.spliterator(), false).count();

    assertThat(actualUsers).isEqualTo(expectedUsers);
    assertThat(actualUsers).containsAll(expectedUsers);
    for (User actualUser : actualUsers) {
      assertThat(expectedUsers).contains(actualUser);
    }
    assertThat(actualUserCount).isEqualTo(2);
    assertThat(actualUserCount).isNotEqualTo(3);
    assertThat(actualUserCount).isNotEqualTo(1);
  }

  @Test
  void getOne() {
    User userToFind = new User(1L, Role.USER,
      "Mr", "Abioye", "Bankole", "bioyexx@yahoo.com",
      "08127350135", new char[]{'p','a','s','s','w','o','r','d'});
      when(repository.findById(1L)).thenReturn(Optional.of(userToFind));
      
      User userFound = userService.getOne(1L);

      assertThat(userFound.getId()).isEqualTo(1L);
      assertThat(userFound.getId()).isEqualTo(userToFind.getId());
      assertThat(userFound).isEqualTo(userToFind);
  }

  @Test()
  void getOneNotFound() {
      when(repository.findById(1L)).thenReturn(Optional.empty());
      
      assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() ->{
        userService.getOne(1L);
      });
  }

  @Test
  void testRegisterUser() {

  }

  @Test
  void testSaveUser() {

  }

  @Test
  void testUpdateUser() {

  }

  @Test
  void testVerifyUser() {

  }
}
