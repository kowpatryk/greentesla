package com.maracuya.app.transactions;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import static com.maracuya.app.transactions.Account.newAccountWithCredit;
import static com.maracuya.app.transactions.Account.newAccountWithDebit;

@Service
public class TransactionsReportService {

    public List<Account> calculateReport(List<Transaction> transactions) {
        Map<String, Account> accounts = new TreeMap<>();
        transactions.forEach(processTransaction(accounts));
        return accounts.values().stream().toList();
    }

    private Consumer<Transaction> processTransaction(Map<String, Account> accounts) {
        return transaction -> {
            processDebitAccountForTransaction(accounts, transaction);
            processCreditAccountForTransaction(accounts, transaction);
        };
    }

    private void processDebitAccountForTransaction(Map<String, Account> accounts, Transaction transaction) {
        accounts.merge(
            transaction.debitAccount(),
            newAccountWithDebit(transaction.debitAccount(), transaction.amount()),
            (oldAccountData, ignored) -> oldAccountData.updateWithDebit(transaction.amount())
        );
    }

    private void processCreditAccountForTransaction(Map<String, Account> accounts, Transaction transaction) {
        accounts.merge(
            transaction.creditAccount(),
            newAccountWithCredit(transaction.creditAccount(), transaction.amount()),
            (oldAccountData, ignored) -> oldAccountData.updateWithCredit(transaction.amount())
        );
    }
}
