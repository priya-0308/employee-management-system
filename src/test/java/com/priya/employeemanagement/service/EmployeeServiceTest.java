package com.priya.employeemanagement.service;

import com.priya.employeemanagement.dto.EmployeeRequest;
import com.priya.employeemanagement.dto.EmployeeResponse;
import com.priya.employeemanagement.entity.Employee;
import com.priya.employeemanagement.exception.ResourceNotFoundException;
import com.priya.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployee_shouldSaveEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Alice",
                "Johnson",
                "alice.johnson@company.com",
                "IT",
                "Developer",
                new BigDecimal("90000"),
                LocalDate.of(2024, 1, 10)
        );

        Employee savedEmployee = new Employee(1L, "Alice", "Johnson", "alice.johnson@company.com",
                "IT", "Developer", new BigDecimal("90000"), LocalDate.of(2024, 1, 10));

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        EmployeeResponse response = employeeService.createEmployee(request);

        assertNotNull(response);
        assertEquals("Alice", response.getFirstName());
        assertEquals("Developer", response.getPosition());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_shouldThrowWhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(99L));

        assertTrue(exception.getMessage().contains("Employee not found with id: 99"));
    }
}
