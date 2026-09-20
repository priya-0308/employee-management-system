package com.priya.employeemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.employeemanagement.config.SecurityConfig;
import com.priya.employeemanagement.dto.EmployeeRequest;
import com.priya.employeemanagement.dto.EmployeeResponse;
import com.priya.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "app.security.admin.username=admin",
        "app.security.admin.password=admin123",
        "app.security.user.username=user",
        "app.security.user.password=user123"
})
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getAllEmployees_shouldReturnOk() throws Exception {
        EmployeeResponse employee = new EmployeeResponse(
                1L,
                "John",
                "Smith",
                "john.smith@company.com",
                "Engineering",
                "Senior Developer",
                new BigDecimal("95000.00"),
                LocalDate.of(2022, 1, 15)
        );

        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].email").value("john.smith@company.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createEmployee_shouldReturnCreated() throws Exception {
        EmployeeRequest request = new EmployeeRequest(
                "Sarah",
                "Davis",
                "sarah.davis@company.com",
                "Marketing",
                "Manager",
                new BigDecimal("85000"),
                LocalDate.of(2024, 3, 12)
        );

        EmployeeResponse response = new EmployeeResponse(
                2L,
                "Sarah",
                "Davis",
                "sarah.davis@company.com",
                "Marketing",
                "Manager",
                new BigDecimal("85000"),
                LocalDate.of(2024, 3, 12)
        );

        when(employeeService.createEmployee(any(EmployeeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Sarah"))
                .andExpect(jsonPath("$.email").value("sarah.davis@company.com"));

        verify(employeeService).createEmployee(any(EmployeeRequest.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void user_shouldNotBeAllowedToCreateEmployee() throws Exception {
        EmployeeRequest request = new EmployeeRequest(
                "James",
                "Taylor",
                "james.taylor@company.com",
                "Operations",
                "Analyst",
                new BigDecimal("70000"),
                LocalDate.of(2024, 5, 5)
        );

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        verify(employeeService, never()).createEmployee(any(EmployeeRequest.class));
    }
}
