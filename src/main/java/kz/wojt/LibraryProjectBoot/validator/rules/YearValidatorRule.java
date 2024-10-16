package kz.wojt.LibraryProjectBoot.validator.rules;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kz.wojt.LibraryProjectBoot.validator.annotation.YearValidator;
import java.time.LocalDate;

public class YearValidatorRule implements ConstraintValidator<YearValidator, Integer> {
    private int min = 0;

    @Override
    public void initialize(YearValidator constraintAnnotation) {
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        return year > min && year < LocalDate.now().getYear();
    }
}