package it.falcao.n26.StatsAPI.repositories;

import it.falcao.n26.StatsAPI.models.Statistics;
import it.falcao.n26.StatsAPI.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static it.falcao.n26.StatsAPI.models.Statistics.emptyStatistics;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

public class TransactionsRepository {
    private Statistics statistics;
    private List<Transaction> transactions;
    private Long youngestTimestamp;

    public TransactionsRepository() {
        transactions = synchronizedList(new ArrayList());
        statistics = emptyStatistics();
        youngestTimestamp = Long.valueOf(0);
    }

    public void putTransaction(Transaction transaction) {
        synchronized (this) {
            youngestTimestamp = transaction.getTimestamp();
            transactions.add(transaction);
            Collections.sort(transactions, transactionTimeComparator());
            statistics.setMin(transactions.stream().mapToDouble(a -> a.getAmount()).min().orElse(transaction.getAmount()));
            statistics.setSum(transactions.stream().mapToDouble(a -> a.getAmount()).sum());
            statistics.setAvg(transactions.stream().mapToDouble(a -> a.getAmount()).average().orElse(transaction.getAmount()));
            statistics.setMax(transactions.stream().mapToDouble(a -> a.getAmount()).max().orElse(transaction.getAmount()));
            statistics.setCount(transactions.stream().count());
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
}
