
package com.example.calculator;

import com.example.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorApplicationTests {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void additionWorks() {
        assertEquals(15.0, calculatorService.add(10, 5));
    }

    @Test
    void subtractionWorks() {
        assertEquals(5.0, calculatorService.subtract(10, 5));
    }

    @Test
    void multiplicationWorks() {
        assertEquals(50.0, calculatorService.multiply(10, 5));
    }

    @Test
    void divisionWorks() {
        assertEquals(2.0, calculatorService.divide(10, 5));
    }

    @Test
    void divisionByZeroThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculatorService.divide(10, 0));
    }

    @Test
    void additionEndpointReturnsResult() throws Exception {
        mockMvc.perform(get("/api/v1/add")
                        .param("a", "10")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(15.0));
    }

    @Test
    void divisionByZeroEndpointReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/divide")
                        .param("a", "10")
                        .param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Division by zero is not allowed"));
    }

    @Test
    void missingParameterReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Missing required parameter: b"));
    }
}

