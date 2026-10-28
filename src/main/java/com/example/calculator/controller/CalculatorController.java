
package com.example.calculator.controller;

import com.example.calculator.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/add")
    public Map<String, Double> add(
            @RequestParam double a,
            @RequestParam double b) {
        return Map.of("result", calculatorService.add(a, b));
    }

    @GetMapping("/subtract")
    public Map<String, Double> subtract(
            @RequestParam double a,
            @RequestParam double b) {
        return Map.of("result", calculatorService.subtract(a, b));
    }

    @GetMapping("/multiply")
    public Map<String, Double> multiply(
            @RequestParam double a,
            @RequestParam double b) {
        return Map.of("result", calculatorService.multiply(a, b));
    }

    @GetMapping("/divide")
    public Map<String, Double> divide(
            @RequestParam double a,
            @RequestParam double b) {
        return Map.of("result", calculatorService.divide(a, b));
    }
}
