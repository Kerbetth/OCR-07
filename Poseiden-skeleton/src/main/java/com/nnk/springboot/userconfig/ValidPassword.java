package com.nnk.springboot.userconfig;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraint.class)
@Retention(RUNTIME)
public @interface ValidPassword {

    String message() default "Invalid Password.It needs to have at least 8 characters (maximum 30), an uppercase, a number and a special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}