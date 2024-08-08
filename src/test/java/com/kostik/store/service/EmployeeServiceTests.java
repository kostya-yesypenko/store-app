package com.kostik.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.kostik.store.domain.User;
import com.kostik.store.repository.UserRepository;

@SpringBootTest
public class EmployeeServiceTests {

    private UserService userService;

    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void testFindByLogin() {
        // Arrange
        String testLogin = "john.doe";
        User mockEmployee = new User();
        mockEmployee.setLogin(testLogin);

        when(userRepository.findByLogin(testLogin)).thenReturn(mockEmployee);

        // Act
        User result = userService.findByLogin(testLogin);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo(testLogin);
    }

    @Test
    public void testGetEmployees() {
        // Arrange
        List<User> mockEmployees = Arrays.asList(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(mockEmployees);

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
    }
}
