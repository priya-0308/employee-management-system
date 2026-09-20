package com.priya.employeemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.employeemanagement.dto.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getAllEmployees_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk());
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

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Sarah"));
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
    }
}
