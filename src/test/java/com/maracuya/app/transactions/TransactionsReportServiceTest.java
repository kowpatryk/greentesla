package com.maracuya.app.transactions;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class TransactionsReportServiceTest {

    private final TransactionsReportService reportService = new TransactionsReportService();

    @Test
    void shouldReturnEmptyListWhenThereAreNoTransactions() {
        List<Account> accounts = reportService.calculateReport(emptyList());

        assertThat(accounts).isEmpty();
    }

    @Test
    void shouldProcessSimpleTransaction() {
        List<Transaction> transactions = List.of(
            new Transaction("1", "2", BigDecimal.valueOf(20.0))
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting("account", "debitCount", "creditCount", "balance")
            .containsExactly(
                tuple("1", 1, 0, BigDecimal.valueOf(-20.0)),
                tuple("2", 0, 1, BigDecimal.valueOf(20.0))
            );
    }

    @Test
    void shouldAggregateMultipleTransactionsForTheSameDebitAccount() {
        List<Transaction> transactions = List.of(
            new Transaction("1", "2", BigDecimal.valueOf(10.0)),
            new Transaction("1", "3", BigDecimal.valueOf(20.0)),
            new Transaction("1", "4", BigDecimal.valueOf(30.0)),
            new Transaction("1", "5", BigDecimal.valueOf(40.0))
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting("account", "debitCount", "creditCount", "balance")
            .containsExactly(
                tuple("1", 4, 0, BigDecimal.valueOf(-100.0)),
                tuple("2", 0, 1, BigDecimal.valueOf(10.0)),
                tuple("3", 0, 1, BigDecimal.valueOf(20.0)),
                tuple("4", 0, 1, BigDecimal.valueOf(30.0)),
                tuple("5", 0, 1, BigDecimal.valueOf(40.0))
            );
    }

    @Test
    void shouldAggregateMultipleTransactionsForTheSameCreditAccount() {
        List<Transaction> transactions = List.of(
            new Transaction("2", "1", BigDecimal.valueOf(10.0)),
            new Transaction("3", "1", BigDecimal.valueOf(20.0)),
            new Transaction("4", "1", BigDecimal.valueOf(30.0)),
            new Transaction("5", "1", BigDecimal.valueOf(40.0))
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting("account", "debitCount", "creditCount", "balance")
            .containsExactly(
                tuple("1", 0, 4, BigDecimal.valueOf(100.0)),
                tuple("2", 1, 0, BigDecimal.valueOf(-10.0)),
                tuple("3", 1, 0, BigDecimal.valueOf(-20.0)),
                tuple("4", 1, 0, BigDecimal.valueOf(-30.0)),
                tuple("5", 1, 0, BigDecimal.valueOf(-40.0))
            );
    }

    @Test
    void shouldReturnAccountsInOrder() {
        List<Transaction> transactions = List.of(
            randomTransaction("1", "3"),
            randomTransaction("5", "2"),
            randomTransaction("2", "7"),
            randomTransaction("4", "8"),
            randomTransaction("4", "6")
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting(Account::account)
            .containsExactly("1", "2", "3", "4", "5", "6", "7", "8");
    }

    private Transaction randomTransaction(String debitAccount, String creditAccount) {
        return new Transaction(debitAccount, creditAccount, BigDecimal.valueOf(new Random().nextDouble()));
    }
}
