package com.maracuya.app.transactions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionValidator implements ConstraintValidator<ValidTransaction, Transaction> {

    private static final int MAX_DECIMAL_PLACES = 2;
    private static final String INVALID_DECIMAL_PLACES_MESSAGE
        = String.format("Amount must have at most %d decimal places.", MAX_DECIMAL_PLACES);
    private static final String SAME_ACCOUNT_NUMBERS_MESSAGE = "Credit and debit accounts must be different.";

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext context) {
        return verifyAccountNumbersAreDifferent(transaction, context)
            && verifyAmountHasValidDecimalPlaces(transaction.amount(), context);
    }

    private boolean verifyAccountNumbersAreDifferent(Transaction transaction, ConstraintValidatorContext context) {
        boolean accountsAreDifferent = !Objects.equals(transaction.creditAccount(), transaction.debitAccount());
        if (!accountsAreDifferent) {
            addConstraintViolation(context, SAME_ACCOUNT_NUMBERS_MESSAGE);
        }
        return accountsAreDifferent;
    }

    private boolean verifyAmountHasValidDecimalPlaces(BigDecimal amount, ConstraintValidatorContext context) {
        boolean validDecimalPlaces = amount.scale() <= MAX_DECIMAL_PLACES;
        if (!validDecimalPlaces) {
            addConstraintViolation(context, INVALID_DECIMAL_PLACES_MESSAGE);
        }
        return validDecimalPlaces;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String errorMessage) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    }
}
