package com.maracuya.app.transactions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class TransactionsController {

    private final TransactionsReportService transactionsReportService;

    public TransactionsController(TransactionsReportService transactionsReportService) {
        this.transactionsReportService = transactionsReportService;
    }

    @PostMapping("/transactions/report")
    public List<Account> executeReport(@RequestBody @Valid @Size(max = 100000) List<Transaction> transactions) {
        return transactionsReportService.calculateReport(transactions);
    }
}
