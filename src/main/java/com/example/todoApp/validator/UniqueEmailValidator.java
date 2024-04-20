package com.example.todoApp.validator;

import com.example.todoApp.annotations.UniqueEmailConstraint;
import com.example.todoApp.repository.UserAccountRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailConstraint, String> {
    private UserAccountRepository userAccountRepository;

    @Override
    public void initialize(UniqueEmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userAccountRepository.findByEmail(value).isEmpty();
    }
}
