package com.maracuya.app.transactions;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@ValidTransaction
public record Transaction(

    @Size(min = 26, max = 26, message = "size must be exactly 26")
    String debitAccount,

    @Size(min = 26, max = 26, message = "size must be exactly 26")
    String creditAccount,

    @Positive
    BigDecimal amount
) {
}
