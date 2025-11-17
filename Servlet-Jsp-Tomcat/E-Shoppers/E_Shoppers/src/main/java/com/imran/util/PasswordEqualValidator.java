package com.imran.util;

import com.imran.annotation.PasswordEqual;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

/**
 * Custom validator that checks whether two fields inside a class
 * (e.g., password and confirmPassword) have the same value.
 *
 * This validator works together with the @PasswordEqual annotation.
 */
public class PasswordEqualValidator implements ConstraintValidator<PasswordEqual, Object> {

    // Field names given from the annotation
    private String firstFieldName;
    private String secondFieldName;

    // Custom validation message provided in the annotation
    private String message;

    /**
     * Initializes the validator with values from the @PasswordEqual annotation.
     * This method runs once when the validator is created.
     */
    @Override
    public void initialize(PasswordEqual constraint) {
        // Get field names from the annotation attributes
        firstFieldName = constraint.first();
        secondFieldName = constraint.second();
        // Store the custom message to use when validation fails
        message = constraint.message();
    }

    /**
     * The actual validation logic that checks whether the two fields are equal.
     *
     * @param value   The object being validated (e.g., a UserDto instance)
     * @param context The validation context, used to build error messages
     * @return true if passwords match, false otherwise
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            // Read the values of the two fields using reflection
            final Object firstObj = getValue(value, firstFieldName);
            final Object secondObj = getValue(value, secondFieldName);

            // Compare the two field values
            // (If either is null, .equals() may throw NPE, but normally DTO ensures they are not null)
            valid = firstObj.equals(secondObj);
        } catch (final Exception ignored) {
            // If any reflection error occurs, ignore it (validation will fail)
        }

        // If passwords do not match, build and attach custom error message
        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        // IMPORTANT: return the actual validation result
        return valid;
    }

    /**
     * Helper method that extracts the value of a given field from the object
     * using Java Reflection.
     *
     * @param value     The object containing the field (e.g., UserDto)
     * @param fieldName The name of the field to read
     * @return The value inside the field
     */
    private Object getValue(Object value, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        // Get metadata about the field (even private fields)
        Field field = value.getClass().getDeclaredField(fieldName);
        // Allow access to private fields
        field.setAccessible(true);

        // Return the actual value stored in the field
        return field.get(value);
    }
}
