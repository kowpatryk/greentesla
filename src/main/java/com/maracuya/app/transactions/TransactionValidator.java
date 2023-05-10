package com.maracuya.app.transactions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class TransactionValidator implements ConstraintValidator<ValidTransaction, Transaction> {

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext context) {
        if (Objects.equals(transaction.creditAccount(), transaction.debitAccount())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Credit and debit accounts must be different")
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
