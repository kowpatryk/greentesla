package com.maracuya.app.transactions;

public record Account(
    String account,
    int debitCount,
    int creditCount,
    double balance
) {

    public Account updateWithDebit(double amount) {
        return new Account(account, debitCount + 1, creditCount, balance - amount);
    }

    public Account updateWithCredit(double amount) {
        return new Account(account, debitCount, creditCount + 1, balance + amount);
    }

    public static Account newAccountWithDebit(String accountNumber, double debitAmount) {
        return new Account(accountNumber, 1, 0, -debitAmount);
    }

    public static Account newAccountWithCredit(String accountNumber, double creditAmount) {
        return new Account(accountNumber, 0, 1, creditAmount);
    }
}
