package it.falcao.n26.StatsAPI.repositories;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static it.falcao.n26.StatsAPI.models.Statistics.emptyStatistics;
import static java.lang.Long.valueOf;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

public class TransactionsRepository {
    private Statistics statistics;
    private List<Transaction> transactions;
    private Long youngestTimestamp;

    public TransactionsRepository() {
        transactions = synchronizedList(new ArrayList());
        statistics = emptyStatistics();
        youngestTimestamp = valueOf(0);
    }

    public void putTransaction(Transaction transaction) {
        synchronized (this) {
            youngestTimestamp = transaction.getTimestamp();
            transactions.add(transaction);

            statistics = Statistics.builder()
                .sum(transactions.stream().mapToDouble(Transaction::getAmount).sum())
                .max(transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(statistics.getMax()))
                .min(transactions.stream().mapToDouble(Transaction::getAmount).min().orElse(statistics.getMin()))
                .avg(transactions.stream().mapToDouble(Transaction::getAmount).average().orElse(statistics.getAvg()))
                .count(valueOf(transactions.size())).build();
        }
    }

    private Comparator<Transaction> transactionTimeComparator() {
        return comparing(Transaction::getTimestamp);
    }

    public Statistics getStatistics() {
        synchronized (this) {
            return statistics.copy();
        }
    }

    public Long latestTimestamp() {
        synchronized (this) {
            return youngestTimestamp;
        }
    }

    public Long flush() {
        synchronized (this) {
            Long count = transactions.stream().count();
            transactions.clear();
            statistics = emptyStatistics();
            return count;
        }
    }
}
