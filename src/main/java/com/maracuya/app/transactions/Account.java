package com.maracuya.app.transactions;

import java.math.BigDecimal;

public record Account(
    String account,
    int debitCount,
    int creditCount,
    BigDecimal balance
) {

    public Account updateWithDebit(BigDecimal amount) {
        return new Account(account, debitCount + 1, creditCount, balance.subtract(amount));
    }

    public Account updateWithCredit(BigDecimal amount) {
        return new Account(account, debitCount, creditCount + 1, balance.add(amount));
    }

    public static Account newAccountWithDebit(String accountNumber, BigDecimal debitAmount) {
        return new Account(accountNumber, 1, 0, debitAmount.negate());
    }

    public static Account newAccountWithCredit(String accountNumber, BigDecimal creditAmount) {
        return new Account(accountNumber, 0, 1, creditAmount);
    }
}
