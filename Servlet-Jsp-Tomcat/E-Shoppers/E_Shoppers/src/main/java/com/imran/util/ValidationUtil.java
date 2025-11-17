package com.imran.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {
    // Singleton instance:
    // We only want a single instance of ValidationUtil throughout the application,
    // so we create it here and provide a global access method.
    private static final ValidationUtil INSTANCE  = new ValidationUtil();

    // Validator instance:
    // This is the core validator from Jakarta Bean Validation.
    // It will be used to validate any object annotated with validation annotations like @NotNull, @Size, @Email, etc.
    private final Validator validator;

    // Private constructor:
    // Prevents direct instantiation of ValidationUtil.
    // Initializes the validator using the default factory.
    private ValidationUtil() {
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    // Public method to get the singleton instance:
    // Ensures there is only one instance of ValidationUtil used throughout the app.
    public static ValidationUtil getInstance() {
        return INSTANCE;
    }

    // Generic validation method:
    // Takes any object `T` annotated with Bean Validation annotations.
    // Returns a map of field names (property paths) to validation error messages.
    public <T> Map<String, String> validate(T Object) {
        // Validate the object and get a set of violations
        var violations = validator.validate(Object);

        // Convert the Set<ConstraintViolation> into a Map<String, String>
        // - Key: property path (field name)
        // - Value: error message
        // - If multiple errors exist for the same field, join them with <br/> for HTML display
        return violations.stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (errorMsg1, errorMsg2) -> errorMsg1 + "<br/>" + errorMsg2));
    }
}
