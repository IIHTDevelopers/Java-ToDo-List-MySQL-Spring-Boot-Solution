package com.todoapplication.boundary;

import static com.todoapplication.utils.TestUtils.boundaryTestFile;
import static com.todoapplication.utils.TestUtils.currentTest;
import static com.todoapplication.utils.TestUtils.testReport;
import static com.todoapplication.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.todoapplication.dto.ToDoDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ToDoBoundaryTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void afterAll() {
        testReport();
    }

    @Test
    public void testTitleNotNull() throws IOException {
        ToDoDTO todoDTO = new ToDoDTO();
        todoDTO.setTitle(null);
        Set<ConstraintViolation<ToDoDTO>> violations = validator.validate(todoDTO);
        try {
            yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
        } catch (Exception e) {
            yakshaAssert(currentTest(), false, boundaryTestFile);
        }
    }

    @Test
    public void testDescriptionNotNull() throws IOException {
        ToDoDTO todoDTO = new ToDoDTO();
        todoDTO.setDescription(null);
        Set<ConstraintViolation<ToDoDTO>> violations = validator.validate(todoDTO);
        try {
            yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
        } catch (Exception e) {
            yakshaAssert(currentTest(), false, boundaryTestFile);
        }
    }

    @Test
    public void testCompletedDefaultValue() throws IOException {
        ToDoDTO todoDTO = new ToDoDTO();
        Set<ConstraintViolation<ToDoDTO>> violations = validator.validate(todoDTO);
        try {
            yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
        } catch (Exception e) {
            yakshaAssert(currentTest(), false, boundaryTestFile);
        }
    }

    @Test
    public void testPriorityRange() throws IOException {
        ToDoDTO todoDTO = new ToDoDTO();
        todoDTO.setPriority(15); // Assuming priority range is limited (e.g., 1-10)
        Set<ConstraintViolation<ToDoDTO>> violations = validator.validate(todoDTO);
        try {
            yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
        } catch (Exception e) {
            yakshaAssert(currentTest(), false, boundaryTestFile);
        }
    }
}
