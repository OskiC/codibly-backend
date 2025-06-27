package com.oc.codiblybackend;

import com.oc.codiblybackend.controller.WeatherValidate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WeatherValidateTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testInvalidLatitude_ShouldFail() {
        WeatherValidate invalid = new WeatherValidate(100f, 20f);
        Set<ConstraintViolation<WeatherValidate>> violations = validator.validate(invalid);
        assertFalse(violations.isEmpty());
    }
}
