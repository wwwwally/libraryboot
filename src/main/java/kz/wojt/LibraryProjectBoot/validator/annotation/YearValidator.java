package kz.wojt.LibraryProjectBoot.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kz.wojt.LibraryProjectBoot.validator.rules.YearValidatorRule;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearValidatorRule.class)
public @interface YearValidator {
    int min();
    String message() default "The year must be between 1900 and current year";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
