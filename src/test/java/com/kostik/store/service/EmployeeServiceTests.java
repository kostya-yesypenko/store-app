package com.kostik.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.kostik.store.domain.Employee;
import com.kostik.store.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTests {

    private EmployeeService employeeService;

    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void testFindByLogin() {
        // Arrange
        String testLogin = "john.doe";
        User mockEmployee = new User();
        mockEmployee.setLogin(testLogin);

        when(employeeRepository.findByLogin(testLogin)).thenReturn(mockEmployee);

        // Act
        User result = employeeService.findByLogin(testLogin);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo(testLogin);
    }

    @Test
    public void testGetEmployees() {
        // Arrange
        List<User> mockEmployees = Arrays.asList(new User(), new User(), new User());

        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        // Act
        List<User> result = employeeService.getUsers();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
    }
}
