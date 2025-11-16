package com.imran.util;



import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.stream.Collectors;


// This is an example of singleton pattern.
public final class ValidationUtil {
    private static final ValidationUtil INSTANCE = new ValidationUtil();
    private final Validator validator;
    private ValidationUtil() {
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public static ValidationUtil getInstance() {
        return INSTANCE;
    }

    public <T>Map<String, String> validate(T obj) {
        var violations = validator.validate(obj);

        // This lamda expression simplify and reduces the code that is given as bellow
        return violations.stream()
                .collect(Collectors.toMap(
                        violation ->
                                violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (eMsg1, eMsg2) -> eMsg1 + " <br/> " + eMsg2
                ));

//        Map<String, String> errors = new HashMap<>();
//        for (ConstraintViolation<UserDTO> violation : violations) {
//            String path = violation.getPropertyPath().toString();
//            if (errors.containsKey(path)) {
//                String eMsg = errors.get(path);
//                errors.put(path, eMsg + " <br/> " + violation.getMessage());
//            } else {
//                errors.put(path, violation.getMessage());
//            }
//        }
//        return errors;
    }
}
