package com.maracuya.app.transactions;

import org.junit.jupiter.api.Test;

import java.util.List;

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
            new Transaction("1", "2", 20.0)
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting("account", "debitCount", "creditCount", "balance")
            .containsExactly(
                tuple("1", 1, 0, -20.0),
                tuple("2", 0, 1, 20.0)
            );
    }

    @Test
    void shouldAggregateMultipleTransactionsForTheSameDebitAccount() {
        List<Transaction> transactions = List.of(
            new Transaction("1", "2", 10.0),
            new Transaction("1", "3", 20.0),
            new Transaction("1", "4", 30.0),
            new Transaction("1", "5", 40.0)
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting("account", "debitCount", "creditCount", "balance")
            .containsExactly(
                tuple("1", 4, 0, -100.0),
                tuple("2", 0, 1, 10.0),
                tuple("3", 0, 1, 20.0),
                tuple("4", 0, 1, 30.0),
                tuple("5", 0, 1, 40.0)
            );
    }

    @Test
    void shouldAggregateMultipleTransactionsForTheSameCreditAccount() {
        List<Transaction> transactions = List.of(
            new Transaction("2", "1", 10.0),
            new Transaction("3", "1", 20.0),
            new Transaction("4", "1", 30.0),
            new Transaction("5", "1", 40.0)
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting("account", "debitCount", "creditCount", "balance")
            .containsExactly(
                tuple("1", 0, 4, 100.0),
                tuple("2", 1, 0, -10.0),
                tuple("3", 1, 0, -20.0),
                tuple("4", 1, 0, -30.0),
                tuple("5", 1, 0, -40.0)
            );
    }

    @Test
    void shouldReturnAccountsInOrder() {
        List<Transaction> transactions = List.of(
            new Transaction("1", "3", 1.0),
            new Transaction("5", "2", 2.0),
            new Transaction("2", "7", 3.0),
            new Transaction("4", "8", 4.0),
            new Transaction("4", "6", 5.0)
        );

        List<Account> accounts = reportService.calculateReport(transactions);

        assertThat(accounts)
            .extracting(Account::account)
            .containsExactly("1", "2", "3", "4", "5", "6", "7", "8");
    }
}
