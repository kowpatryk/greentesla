package com.maracuya.app.transactions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = TransactionValidator.class)
public @interface ValidTransaction {

    String message() default "Invalid transaction";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
